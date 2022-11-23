package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstpro.activity.teactivity.teacherMainPageAbility;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

//登录界面
public class MainActivity extends MyActivity {

    private Button reg_button;
    private Button login_button;
    private EditText account;
    private EditText password;
    private Context context;

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


        String uname = AutoLoginStatic.getInstance().getUserNum(context);
        String pass = AutoLoginStatic.getInstance().getPassword(context);
        boolean isteacher = AutoLoginStatic.getInstance().isTeacher(context);

        if(uname.equals("12345")&&pass.equals("12345678")&&isteacher){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, teacherMainPageAbility.class);

            startActivity(intent);
            MainActivity.this.finish();
        }


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
                }else if(account_str.equals("12345")&&password_str.equals("12345678")){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.loginicon)
                        .setTitle("登录")
                        .setMessage("确认登录？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                AutoLoginStatic.getInstance().setUserNum(account_str,context);
                                AutoLoginStatic.getInstance().setPassword(password_str,context);
                                AutoLoginStatic.getInstance().setTeacher(true,context);
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, teacherMainPageAbility.class);

                                startActivity(intent);
                                MainActivity.this.finish();
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
                else{
                    Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}