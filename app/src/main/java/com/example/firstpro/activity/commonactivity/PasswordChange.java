package com.example.firstpro.activity.commonactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

public class PasswordChange extends MyActivity {

    private EditText op;
    private EditText np;
    private EditText anp;
    private Button cpy;
    private Toolbar toolbar;
    private Context context;

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
                String account = AutoLoginStatic.getInstance().getUserNum(context);

                if(TextUtils.isEmpty(op_str)||TextUtils.isEmpty(np_str)||TextUtils.isEmpty(anp_str)){
                    Toast.makeText(getApplicationContext(),"各项均不能为空",Toast.LENGTH_SHORT).show();
                }else if(!op_str.equals("12345678")){
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
                                    update(account,np_str);
                                    Intent intent = new Intent();
                                    intent.setClass(PasswordChange.this, PersonIformationActivity.class);

                                    startActivity(intent);
                                    ActivityCollector.finishOneActivity(PasswordChange.class.getName());
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

        MySQLIteHelper helper = MySQLIteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()) {

            String psql = "update users set password =? where account =?";
            db.execSQL(psql,new Object[]{password,account});

        }
        db.close();
        }

}