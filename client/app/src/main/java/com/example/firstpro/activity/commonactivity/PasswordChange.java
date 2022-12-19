package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.firstpro.data.Me;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

import java.lang.ref.WeakReference;

public class PasswordChange extends MyActivity {

    private EditText op;
    private EditText np;
    private EditText anp;
    private Button cpy;
    private Toolbar toolbar;
    private Context context;

    private  String account = AutoLoginStatic.getInstance().getUserNum(this);
    private ServerService sv =new ServerService();
    private MyHandler myHandler= new MyHandler(this);
    private String pas=AutoLoginStatic.getInstance().getPassword(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        context=this;

        ButtonReact();
    }

    private void ButtonReact(){
        op = (EditText) findViewById(R.id.o_password_edit);
        np = (EditText) findViewById(R.id.n_password_edit);
        anp = (EditText) findViewById(R.id.n_again_password_edit);

        toolbar = (Toolbar) findViewById((R.id.change_password_toolbar)) ;
        cpy = (Button) findViewById(R.id.c_p_yes);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChange.this.finish();
            }
        });

        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op_str = op.getText().toString().trim();
                String np_str = np.getText().toString().trim();
                String anp_str= anp.getText().toString().trim();

                if(TextUtils.isEmpty(op_str)||TextUtils.isEmpty(np_str)||TextUtils.isEmpty(anp_str)){
                    Toast.makeText(getApplicationContext(),"各项均不能为空",Toast.LENGTH_SHORT).show();
                }else if(!op_str.equals(pas)){
                    //ToChange:需从数据库中取得对应账号对应的原密码
                    Toast.makeText(getApplicationContext(),"原密码不正确",Toast.LENGTH_SHORT).show();
                }else if(np_str.length()<8){
                    Toast.makeText(getApplicationContext(),"新密码长度需要大于等于8",Toast.LENGTH_SHORT).show();
                }else if(!np_str.equals(anp_str)){
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(PasswordChange.this);
                    builder.setIcon(R.drawable.leave)
                            .setTitle("修改密码")
                            .setMessage("确定修改？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //更新密码
                                    RemotePChange(np_str);
//                                    update(account,np_str);//更新本地数据库密码
//                                    //因为修改了新密码，所以再登录时需重新输入密码，故把SP中password改为空
//                                    AutoLoginStatic.getInstance().setPassword("",context);
//                                    Intent intent = new Intent();
//                                    //退回主界面
//                                    intent.setClass(PasswordChange.this, MainActivity.class);
//
//                                    startActivity(intent);
//                                    ActivityCollector.finishOtherActivity(MainActivity.class.getName());
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

    private void update (String account, String password){

        //先更新远端数据库，再更新本地数据库
        //ToDo:更新远端数据库


        MySQLIteHelper helper = MySQLIteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()) {

            String psql = "update users set password =? where account =?";
            db.execSQL(psql,new Object[]{password,account});

        }
        db.close();
        }

       private void RemotePChange(String np_str){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = sv.ChangeMess(account, MyURL.LoginURL,true,np_str,null,null,null);
                Message msg = myHandler.obtainMessage();
                if(flag){
                    msg.arg1 =1;
                }
                msg.obj = np_str;
                msg.what=1;
                myHandler.sendMessage(msg);
            }
        }).start();
       }

    private  class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<PasswordChange> weakReference;

        public MyHandler(PasswordChange activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String np =(String) msg.obj;
            switch (msg.what) {
                case 1:
                    if(msg.arg1 ==1){
                        update(account,np);//更新本地数据库密码
                        //因为修改了新密码，所以再登录时需重新输入密码，故把SP中password改为空
                        AutoLoginStatic.getInstance().setPassword("",context);
                        Intent intent = new Intent();
                        //退回主界面
                        intent.setClass(PasswordChange.this, MainActivity.class);

                        startActivity(intent);
                        ActivityCollector.finishOtherActivity(MainActivity.class.getName());
                    }else{
                        Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}