package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.example.firstpro.activity.stuactivity.StuMainPageActivity;
import com.example.firstpro.activity.teactivity.teacherMainPageAbility;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;
import com.example.firstpro.helper.NetJudgeHelper;

import java.lang.ref.WeakReference;
import java.util.List;

//登录界面
public class MainActivity extends MyActivity {

    private Button reg_button;
    private Button login_button;
    private EditText account;
    private EditText password;
    private Context context;

    private MyHandler myHandler = new MyHandler(this);
    private List<Boolean> list1;
    private List<Boolean> list2;
    private ServerService service = new ServerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButtonReact();
        createDB();
    }

    private void createDB() {

        SQLiteOpenHelper helper = MySQLIteHelper.getInstance(context);
        SQLiteDatabase db =helper.getReadableDatabase();
        db.close();
    }

    //注册界面
    private void ButtonReact(){


        reg_button = (Button) findViewById(R.id.btn_register);

        login_button =(Button) findViewById(R.id.btn_login) ;

        account = (EditText) findViewById(R.id.main_account_edit) ;

        password = (EditText)findViewById(R.id.main_password_edit) ;

        account.setText(AutoLoginStatic.getInstance().getUserNum(context));
        password.setText(AutoLoginStatic.getInstance().getPassword(context));

       //从内存中获取相应数据
        String uname = AutoLoginStatic.getInstance().getUserNum(context);
        String pass = AutoLoginStatic.getInstance().getPassword(context);
        boolean isteacher = AutoLoginStatic.getInstance().isTeacher(context);



        //若账号存在，此时其实是做核数据库内容的比较
        //ToChange:
        if(NetJudgeHelper.isNetworkAvailable(context)) {
            AutoLoginFromServer(uname, pass);
        }else{
            Intent intent = new Intent();
            if(isteacher) {//老师
                intent.setClass(MainActivity.this, teacherMainPageAbility.class);
            }else{//学生
                intent.setClass(MainActivity.this, StuMainPageActivity.class);
            }
            startActivity(intent);
            MainActivity.this.finish();
        }


//        if(uname.equals("12345")&&pass.equals("12345678")&&isteacher){
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, teacherMainPageAbility.class);
//
//            startActivity(intent);
//            MainActivity.this.finish();
//        }else if(uname.equals("678910")&&pass.equals("12345678")&&!isteacher){
//            //若为学生：ToDo：
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, StuMainPageActivity.class);
//            startActivity(intent);
//
//            MainActivity.this.finish();
//        }


        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });

        String account_str = account.getText().toString().trim();

        String password_str = password.getText().toString().trim();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account_str = account.getText().toString().trim();
                String password_str = password.getText().toString().trim();

                if (TextUtils.isEmpty(account_str) || TextUtils.isEmpty(password_str)) {
                    Toast.makeText(getApplicationContext(),"用户名或密码为空",Toast.LENGTH_SHORT).show();
                }else {
                    //若账号存在
                    //判断条件从数据库中获取
                    LoginServer(account_str,password_str);
//                    boolean isTea = true;//需改成按照账号从数据库中获取
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setIcon(R.drawable.loginicon)
//                        .setTitle("登录")
//                        .setMessage("确认登录？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                AutoLoginStatic.getInstance().setUserNum(account_str,context);
//                                AutoLoginStatic.getInstance().setPassword(password_str,context);
//                                if(isTea) {//老师
//                                    AutoLoginStatic.getInstance().setTeacher(true, context);
//                                }else{//学生
//                                    AutoLoginStatic.getInstance().setTeacher(false, context);
//                                }
//                                Intent intent = new Intent();
//                                if(isTea) {
//                                    intent.setClass(MainActivity.this, teacherMainPageAbility.class);
//                                }else{
//                                    //ToDo:
//                                    intent.setClass(MainActivity.this, StuMainPageActivity.class);
//                                }
//                                startActivity(intent);
//                                MainActivity.this.finish();
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //无事发生
//                                ;
//                            }
//                        })
//                        .create()
//                        .show();
            }
//                else{
//                    Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


   //自动登录先和数据库中内容比对
    private void AutoLoginFromServer(String user_account,String user_password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                list1 = service.LoginQuery(user_account,user_password, MyURL.LoginURL);
                Message msg = myHandler.obtainMessage();
                if(list1.get(0) ){
                    msg.obj = list1;
                    msg.what = 1;
                    myHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void LoginServer(String user_account,String user_password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                list2 = service.LoginQuery(user_account,user_password, MyURL.LoginURL);
                Message msg = myHandler.obtainMessage();

                    msg.obj = list2;
                    msg.what = 2;
                    myHandler.sendMessage(msg);

            }
        }).start();
    }

    private class MyHandler extends Handler{
        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                //自动登录
                case 1:
                   List<Boolean> list = (List<Boolean>) msg.obj;
                    Intent intent = new Intent();
                    if(list.get(1)){
                        intent.setClass(MainActivity.this, teacherMainPageAbility.class);
                    }else{
                        intent.setClass(MainActivity.this, StuMainPageActivity.class);
                    }
                    startActivity(intent);
                    MainActivity.this.finish();
                    break;
                    //根据账号密码验证登录
                case 2:
                    List<Boolean> _list = (List<Boolean>) msg.obj;
                    if(!_list.get(2)){
                        Toast.makeText(getApplicationContext(),"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }else if(!_list.get(0)){
                        Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }else {
                        String account_str = account.getText().toString().trim();
                        String password_str = password.getText().toString().trim();

                        boolean isTea = _list.get(1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setIcon(R.drawable.loginicon)
                                .setTitle("登录")
                                .setMessage("确认登录？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent = new Intent();
                                        AutoLoginStatic.getInstance().setUserNum(account_str,context);
                                        AutoLoginStatic.getInstance().setPassword(password_str,context);
                                        if(isTea) {//老师
                                            AutoLoginStatic.getInstance().setTeacher(true, context);
                                            intent.setClass(MainActivity.this, teacherMainPageAbility.class);
                                        }else{//学生
                                            AutoLoginStatic.getInstance().setTeacher(false, context);
                                            intent.setClass(MainActivity.this, StuMainPageActivity.class);
                                        }
                                        startActivity(intent);
                                        MainActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //无事发生
                                        ;
                                    }
                                })
                                .create()
                                .show();
                    }
                    break;
            }
        }
   }

}