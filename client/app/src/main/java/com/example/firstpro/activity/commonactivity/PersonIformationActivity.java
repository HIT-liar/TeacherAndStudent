package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.data.Me;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.helper.NetJudgeHelper;

import java.lang.ref.WeakReference;

public class PersonIformationActivity extends MyActivity {

    private TextView account_text;
    private TextView name_text;
    private TextView sex_text;
    private Context context;
    private String user_account;

    private MyHandler myHandler = new MyHandler(this);
    private ServerService sv = new ServerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_iformation);
        context =this;

        setText();
        ButtonReact();
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        Button button_change = (Button) findViewById(R.id.change_btn_pinfo);
        Button button_leave = (Button) findViewById(R.id.leave);
        Button password_change_btn = (Button) findViewById(R.id.change_password) ;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonIformationActivity.this.finish();
            }
        });
       //修改相关信息
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PersonIformationActivity.this, PMessageChange.class);

                startActivity(intent);
            }
        });

        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(PersonIformationActivity.this);
                builder.setIcon(R.drawable.leave)
                        .setTitle("退出登录")
                        .setMessage("确定退出当前账号？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //退出登录需重新输入密码
                                AutoLoginStatic.getInstance().setPassword("",context);
                                Intent intent = new Intent();
                                intent.setClass(PersonIformationActivity.this, MainActivity.class);

                                startActivity(intent);
                                ActivityCollector.finishOtherActivity(MainActivity.class.getName());
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
        });

        password_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonIformationActivity.this, PasswordChange.class);

                startActivity(intent);
            }
        });
    }

    public void setText(){
        account_text =(TextView) findViewById(R.id.per_num);
        name_text = (TextView) findViewById(R.id.per_name);
        sex_text = (TextView) findViewById(R.id.per_sex);
        user_account = AutoLoginStatic.getInstance().getUserNum(context);

        boolean find_in_local = true;
        //只要联网了，就取远端数据并更新本地数据库
        if(NetJudgeHelper.isNetworkAvailable(context)){
            find_in_local=false;//就不用本地设置了
            SetMsgFromRemote();
        }
        //未联网时从本地数据库获取
        if(find_in_local) {
            MySQLIteHelper helper = MySQLIteHelper.getInstance(context);
            String sql = "select account,name,gender from users";
            Cursor cursor = helper.query(sql, null);
            int size = cursor.getCount();
            //下标
            int account_index = cursor.getColumnIndex("account");
            int name_index = cursor.getColumnIndex("name");
            int gender_index = cursor.getColumnIndex("gender");

            for (int i = 0; i < size; i++) {
                cursor.moveToNext();
                if (cursor.getString(account_index).equals(user_account)) {
                    account_text.setText(user_account);
                    name_text.setText(cursor.getString(name_index));
                    int gender = cursor.getInt(gender_index);
                    if (gender == 1) {
                        sex_text.setText("男");
                    } else {
                        sex_text.setText("女");
                    }
                }
            }
        }
//       if(!find_in_local) {
//            account_text.setText("12345");
//            name_text.setText("李四");
//            sex_text.setText("男");
//        }
    }

    private  void SetMsgFromRemote(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Me me = sv.MessageQuery(user_account, MyURL.LoginURL);
                Message msg = myHandler.obtainMessage();
                if(me!=null){
                    msg.obj = me;
                    msg.what = 1;
                    myHandler.sendMessage(msg);
                }

            }
        }).start();
    }

    private  class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<PersonIformationActivity> weakReference;

        public MyHandler(PersonIformationActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Me me = (Me) msg.obj;
            switch (msg.what) {
                case 1:
                   account_text.setText(me.getAccount());
                   name_text.setText(me.getName());
                   if(me.getGender()){
                       sex_text.setText("男");
                   }else{
                       sex_text.setText("女");
                   }
                   me.setPassword(AutoLoginStatic.getInstance().getPassword(context));
                   MySQLIteHelper.getInstance(context).update(me);
                   break;
            }
        }
    }
}