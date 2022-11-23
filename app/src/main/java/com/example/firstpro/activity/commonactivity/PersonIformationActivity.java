package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;

public class PersonIformationActivity extends MyActivity {

    private TextView account_text;
    private TextView name_text;
    private TextView sex_text;
    private Context context;

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

        account_text.setText("12345");
        name_text.setText("李四");
        sex_text.setText("男");
    }
}