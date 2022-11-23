package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.signhelper.signjudge;

public class OnClassActivity extends MyActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_class);
        signjudge.sign_over = false;
        ButtonReact();
    }

    private void ButtonReact(){
        Button button_sign = (Button) findViewById(R.id.button_sign);
        Button button_deliver = (Button) findViewById(R.id.button_deliver);
        Button button_ask = (Button) findViewById(R.id.button_ask);
        Button button_over= (Button) findViewById(R.id.button_over);

        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                if(!signjudge.sign_over){
                    intent.setClass(OnClassActivity.this, SignInActivity.class);
                }
                else{
                    intent.setClass(OnClassActivity.this, StuIsSignActivity.class);
                }

                startActivity(intent);

            }
        });

        button_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(OnClassActivity.this, SendActivity.class);

                startActivity(intent);

            }
        });

        button_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(OnClassActivity.this, askActivity.class);

                startActivity(intent);
            }
        });

        button_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(OnClassActivity.this);
                builder.setIcon(R.drawable.over)
                        .setTitle("结束课程")
                        .setMessage("确定结束课程？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCollector.finishOneActivity(OnClassActivity.class.getName());
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