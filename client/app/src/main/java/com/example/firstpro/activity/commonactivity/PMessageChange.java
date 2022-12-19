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
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

import java.lang.ref.WeakReference;


public class PMessageChange extends MyActivity {


    private EditText name_edit;
    private Button sex_change;
    private Button change_yes_btn;
    private Toolbar toolbar;
    private Context context;

    private ServerService sv = new ServerService();
    MyHandler myHandler = new MyHandler(this);
    String user_id = AutoLoginStatic.getInstance().getUserNum(this);
    private String n_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmessage_change);
        context = this;

        setEdit();
        ButtonReact();
    }


    private void ButtonReact() {

        sex_change = (Button) findViewById(R.id.sex_change);
        final boolean[] sexflag = {false, false, false};

        change_yes_btn = (Button) findViewById(R.id.change_yes);

        name_edit = (EditText) findViewById(R.id.per_change_name);

        toolbar = (Toolbar) findViewById(R.id.pchange_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMessageChange.this.finish();
            }
        });

        sex_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.sex_popwin, null);

                RadioGroup radioGroup = (RadioGroup) popupView.findViewById(R.id.changesex_radioGroup);

                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAsDropDown(v);

                RadioButton man = (RadioButton) popupView.findViewById(R.id.change_sex_man);
                RadioButton woman = (RadioButton) popupView.findViewById(R.id.change_sex_women);
                if (sexflag[0]) {
                    if (sexflag[1] && !sexflag[2]) {
                        man.setChecked(true);
                    } else if (!sexflag[1] && sexflag[2]) {
                        woman.setChecked(true);
                    }
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        sexflag[0] = true;
                        if (checkedId == R.id.change_sex_man) {

                            sexflag[1] = true;
                            sexflag[2] = false;
                        } else {

                            sexflag[2] = true;
                            sexflag[1] = false;
                        }
                    }
                });


            }
        });


        change_yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_edit.getText().toString().trim();
                n_name = name;
                String account = AutoLoginStatic.getInstance().getUserNum(context);
                if (sexflag[0]) {
                    //更新性别
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PMessageChange.this);
                    builder.setIcon(R.drawable.change)
                            .setTitle("修改资料")
                            .setMessage("确认修改？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   //修改远端数据库，同时更改本地数据库
                                    ChangeMsg(name,sexflag);
//                                    update(account, name, sexflag);
//                                    ActivityCollector.finishOneActivity(PersonIformationActivity.class.getName());
//                                    Intent intent = new Intent();
//                                    intent.setClass(PMessageChange.this, PersonIformationActivity.class);
//                                    startActivity(intent);
//                                    ActivityCollector.finishOneActivity(PMessageChange.class.getName());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ;
                                }
                            })
                            .create()
                            .show();
                }
            }
        });
    }

    private void setEdit() {


        //本地肯定有内容，直接用本地

        name_edit = (EditText) findViewById(R.id.per_change_name);

        String account = AutoLoginStatic.getInstance().getUserNum(context);
        //从本地数据库中根据用户账号来查询用户名并显示
        MySQLIteHelper mySQLIteHelper = MySQLIteHelper.getInstance(context);
        SQLiteDatabase db = mySQLIteHelper.getReadableDatabase();

        String sql = "select name from users where account=" + account;

        Cursor cursor = mySQLIteHelper.query(sql, null);
        int size = cursor.getCount();
        int index = cursor.getColumnIndex("name");
        //其实就有一个
        for (int i = 0; i < size; i++) {
            cursor.moveToNext();
            name_edit.setText(cursor.getString(index));
        }
        cursor.close();
        db.close();

        //从远端拉取
        //ToDo：
        //name_edit.setText("李四");
    }

    private void update(String account, String name, boolean[] flags) {

        //先更新远端数据库，再更新本地数据库
        //这里直接更新远程数据库，就不通过服务器更新了
        //ToDo:更新远端数据库


        //更新本地数据库
        MySQLIteHelper helper = MySQLIteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db.isOpen()) {
            String nsql = "update users set name =? where account =?";
            db.execSQL(nsql, new Object[]{name, account});

            if (flags[0] == true) {
                String gsql = "update users set gender =? where account =?";
                if (flags[1] == true && flags[2] == false) {
                    db.execSQL(gsql, new Object[]{1, account});

                } else if (flags[1] == false && flags[2] == true) {
                    db.execSQL(gsql, new Object[]{0, account});
                }
            }
        }
        db.close();
    }

    private void ChangeMsg(String new_name,boolean flags[]) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag=false;
                if (flags[0] == true) {
                    if (flags[1] == true && flags[2] == false) {
                         flag =sv.ChangeMess(user_id, MyURL.LoginURL,false,null,new_name,
                                true,true);
                    } else if (flags[1] == false && flags[2] == true) {
                         flag =sv.ChangeMess(user_id, MyURL.LoginURL,false,null,new_name,
                                true,false);
                    }
                }else{
                     flag =sv.ChangeMess(user_id, MyURL.LoginURL,false,null,
                            new_name,false,false);
                }

                Message msg= myHandler.obtainMessage();
                if(flag){
                      msg.arg1 =1;
                }else{
                    msg.arg1 =2;
                }
                msg.obj = flags;
                msg.what =1;
                myHandler.sendMessage(msg);

            }
        }).start();
    }


    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<PMessageChange> weakReference;

        public MyHandler(PMessageChange activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             boolean[] flags = (boolean[]) msg.obj;
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == 1) {
                        update(user_id, n_name, flags);
                        ActivityCollector.finishOneActivity(PersonIformationActivity.class.getName());
                        Intent intent = new Intent();
                        intent.setClass(PMessageChange.this, PersonIformationActivity.class);
                        startActivity(intent);
                        break;
                    }
            }
        }
    }
}