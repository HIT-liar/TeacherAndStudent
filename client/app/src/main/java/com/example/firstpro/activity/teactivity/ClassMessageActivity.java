package com.example.firstpro.activity.teactivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.listviewadapter.StuListAdapter;
import com.example.firstpro.data.Student;
import com.example.firstpro.database.ClassesSQLIteHelper;

import java.lang.ref.WeakReference;
import java.util.List;

public class ClassMessageActivity extends MyActivity {

    private Intent intent;
    private Context context;
    private String classid="";

    private ServerService sv = new ServerService();
    MyHandler myHandler = new MyHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_message);
        context=this;

        setText();
        ButtonReact();
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.c_m_toolbar);
        ImageButton stulist_btn = (ImageButton) findViewById(R.id.stulist_btn);
        Button delete = (Button) findViewById(R.id.delete_class_btn) ;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassMessageActivity.this.finish();
            }
        });

        stulist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("classid",classid);
                intent.setClass(ClassMessageActivity.this, ChosenClassStudentsActivity.class);
                intent.putExtra("bun",bundle);
                startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在本地数据库上删除课程
                ClassesSQLIteHelper.getInstance(context).deleteOne(classid);
//                ActivityCollector.finishOneActivity(myclassesActivity.class.getName());

                //在远端数据库上删除该课程
                //ToDo:远端数据库删除课程
                DeleteRemoteClass();

//                Intent intent = new Intent().setClass(ClassMessageActivity.this,myclassesActivity.class);
//                startActivity(intent);
//                ClassMessageActivity.this.finish();


            }
        });

    }

    private void setText(){

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");

        TextView class_id = (TextView) findViewById(R.id.class_class_id_message) ;
       classid =  bundle.getString("class_id");
        class_id .setText(classid);

        TextView classname = (TextView) findViewById(R.id.class_name_message);
        classname.setText(bundle.getString("name"));

        TextView classnum = (TextView) findViewById(R.id.class_num_message);
        classnum.setText(bundle.getString("num"));

        TextView class_max_stu = (TextView) findViewById(R.id.class_maxstu_message);
        class_max_stu.setText(bundle.getString("stu_max"));

        TextView class_true_stu = (TextView) findViewById(R.id.class_stunum_message);
        class_true_stu.setText(bundle.getString("stu_true"));

        TextView credit = (TextView) findViewById((R.id.class_credit_message));
        credit.setText(bundle.getString("credit"));

        TextView location = (TextView) findViewById((R.id.class_location_message));
        location.setText(bundle.getString("location"));

        TextView time = (TextView) findViewById((R.id.class_time_message));
        time.setText(bundle.getString("time"));

    }


    public void DeleteRemoteClass(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = sv.DeleteMyClass(classid, MyURL.TeaURL,true,null,false);
                Message msg = myHandler.obtainMessage();
                if(flag){
                    msg.arg1 = 1;
                }else{
                    msg.arg1 = 2;
                }
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<ClassMessageActivity> weakReference;

        public MyHandler(ClassMessageActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(msg.arg1 == 1){
                        Intent intent = new Intent().setClass(ClassMessageActivity.this,myclassesActivity.class);
                        ActivityCollector.finishOneActivity(myclassesActivity.class.getName());
                        startActivity(intent);
                        ClassMessageActivity.this.finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


}