package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.signhelper.signjudge;

public class SignInActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButtonReact();
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.sign_toolbar);

        Button button_start = (Button) findViewById(R.id.startsign_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInActivity.this.finish();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(SignInActivity.this);
                builder.setIcon(R.drawable.signin)
                        .setTitle("发布签到")
                        .setMessage("是否发布签到？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                signjudge.sign_over=true;
                                SignInActivity.this.finish();

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
    }
}