package com.example.firstpro.activity.commonactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.data.Me;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

public class RegisterActivity extends MyActivity {

    private Toolbar toolbar;
    private Button buttonTea;
    private Button buttonStu;

    private EditText name_edit;
    private EditText password_edit;
    private EditText again_password;
    private EditText account_edit;
    private Context context;


    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        ButtonReact();
    }

    private void ButtonReact(){

        toolbar = findViewById(R.id.reg_Toolbar);
        buttonTea = (Button) findViewById(R.id.teacher_res) ;
        buttonStu = (Button) findViewById(R.id.student_res) ;

        name_edit = (EditText) findViewById(R.id.reg_name_edit) ;
        password_edit = (EditText) findViewById(R.id.reg_password_edit) ;
        again_password = (EditText) findViewById(R.id.reg_password_again_edit) ;
        account_edit = (EditText) findViewById(R.id.reg_account_edit) ;


        radioGroup = (RadioGroup) findViewById(R.id.reg_radioGroup) ;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

        final boolean[] sexflag = {false,false};

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sexflag[0] = true;
                if(checkedId == R.id.reg_sex_man){
                   sexflag[1]=true;
                }
            }
        });

        buttonTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = name_edit.getText().toString().trim();
                String password = password_edit.getText().toString().trim();
                String password_again = again_password.getText().toString().trim();
                String account = account_edit.getText().toString().trim();




                if(!sexflag[0]|| TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_again) || TextUtils.isEmpty(account) ){
                    Toast.makeText(getApplicationContext(),"您输入的信息不完善",Toast.LENGTH_SHORT).show();
                }else if(account.length()<3){
                    Toast.makeText(getApplicationContext(),"账号长度不能小于3位",Toast.LENGTH_SHORT).show();
                }else if(password.length()<8){
                    Toast.makeText(getApplicationContext(),"密码长度不能小于8位",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(password_again)){
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setIcon(R.drawable.reg)
                            .setTitle("教师注册")
                            .setMessage("确认注册？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Me me = new Me(account,password,name,sexflag[1],true);
                                    MySQLIteHelper.getInstance(context).insertUser(me);
                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    ActivityCollector.finishOneActivity(RegisterActivity.class.getName());
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

        buttonStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_edit.getText().toString().trim();
                String password = password_edit.getText().toString().trim();
                String password_again = again_password.getText().toString().trim();
                String account = account_edit.getText().toString().trim();


                if(!sexflag[0]|| TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_again) || TextUtils.isEmpty(account) ){
                    Toast.makeText(getApplicationContext(),"您输入的信息不完善",Toast.LENGTH_SHORT).show();
                }else if(account.length()<5||account.length()>15){
                    Toast.makeText(getApplicationContext(),"账号长度错误（需要5-15位）",Toast.LENGTH_SHORT).show();
                }else if(password.length()<8){
                    Toast.makeText(getApplicationContext(),"密码长度不能小于8位",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(password_again)){
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setIcon(R.drawable.reg)
                            .setTitle("学生注册")
                            .setMessage("确认注册？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Me me = new Me(account,password,name,sexflag[1],false);
                                    MySQLIteHelper.getInstance(context).insertUser(me);
                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    ActivityCollector.finishOneActivity(RegisterActivity.class.getName());
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


}
