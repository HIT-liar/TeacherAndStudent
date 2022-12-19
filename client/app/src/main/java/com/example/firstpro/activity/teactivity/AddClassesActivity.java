package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.commonactivity.MainActivity;
import com.example.firstpro.activity.commonactivity.RegisterActivity;
import com.example.firstpro.data.Me;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.data.Class;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class AddClassesActivity extends MyActivity {


    private EditText classname;
    private EditText classnum;
    private EditText credit;
    private EditText classMAXnum;
    private EditText location;
    private EditText classtime;
    private EditText class_id;
    private Context context;

    private MyHandler myHandler = new MyHandler(this);
    private ServerService sv = new ServerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classes);
        context = this;
        ButtonReact();
    }

    private void ButtonReact(){

        classname = (EditText) findViewById(R.id.add_class_name_edit) ;
        classnum = (EditText) findViewById(R.id.add_class_num_edit) ;
        credit = (EditText) findViewById(R.id.add_credit_num_edit) ;
        classMAXnum = (EditText) findViewById(R.id.add_max_num_edit) ;
        location = (EditText) findViewById(R.id.add_location_num_edit) ;
        classtime = (EditText) findViewById(R.id.add_time_edit) ;
        class_id = (EditText) findViewById(R.id.add_class_id_edit) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.addclasses_toolbar);

        Button button_yes = (Button) findViewById(R.id.addClass_yes);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddClassesActivity.this.finish();
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //获得EditText中的内容
                String classname_str = classname.getText().toString().trim();
                String classnum_str = classnum.getText().toString().trim();
                int classnum_int = Integer.valueOf(classnum_str);
                String credit_str = credit.getText().toString().trim();
                double credit_double = Double.valueOf(credit_str);
                String classMaxnum_str = classMAXnum.getText().toString().trim();
                int classMaxnum_int = Integer.valueOf(classMaxnum_str);
                String location_str = location.getText().toString().trim();
                String classtime_str = classtime.getText().toString().trim();
                String classid_str = class_id.getText().toString().trim();

                String account = AutoLoginStatic.getInstance().getUserNum(context);

                if(TextUtils.isEmpty(classname_str)||TextUtils.isEmpty(classnum_str)||
                        TextUtils.isEmpty(credit_str)||TextUtils.isEmpty(classMaxnum_str)||
                        TextUtils.isEmpty(location_str)||TextUtils.isEmpty(classtime_str)||
                TextUtils.isEmpty(classid_str)){
                    Toast.makeText(getApplicationContext(),"您输入的信息不完善",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddClassesActivity.this);
                    builder.setIcon(R.drawable.addclass)
                            .setTitle("添加课程")
                            .setMessage("确定添加？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Class myclass = new Class(classid_str,classname_str,classnum_int,classMaxnum_int,credit_double,location_str,classtime_str,account,0);
                                    AddToRemote(myclass);
//                                    ClassesSQLIteHelper.getInstance(context).insertClass(myclass);
//                                    Intent intent = new Intent();
//                                    intent.setClass(AddClassesActivity.this, myclassesActivity.class);
//
//                                    startActivity(intent);
//                                    AddClassesActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                }
            }
        });
    }

    private void AddToRemote(Class the_class){
        new Thread(new Runnable() {
            @Override
            public void run() {
             String flag = sv.AddClassToRemote(the_class, MyURL.TeaURL);
             Message msg = myHandler.obtainMessage();
             msg.what=1;
             if(flag.equals("true")){
                 msg.arg1=1;
             }else if(flag.equals("repeat")){
                 msg.arg1=2;
             }else{
                 msg.arg1=3;
             }
             msg.obj = the_class;
             myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<AddClassesActivity> weakReference;

        public MyHandler(AddClassesActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            Class _class;
            switch (msg.what) {
                case 1:
                    if(msg.arg1==1){
                        _class = (Class) msg.obj;
                        ClassesSQLIteHelper.getInstance(context).insertClass(_class);
                        Intent intent = new Intent();
                        intent.setClass(AddClassesActivity.this, myclassesActivity.class);

                        startActivity(intent);
                        AddClassesActivity.this.finish();
                    }else if(msg.arg1==2){
                        Toast.makeText(getApplicationContext(),"添加失败,课程号已存在",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"添加失败,请检查网络",Toast.LENGTH_SHORT).show();
                    }
                    break;


            }
        }
    }

}