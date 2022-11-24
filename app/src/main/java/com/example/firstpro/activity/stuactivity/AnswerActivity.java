package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;

public class AnswerActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        ButtonReact();
    }

    private void ButtonReact(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.answerQuestion_toolbar);

        Button button_yes = (Button) findViewById(R.id.button_stu_submit_answer);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AnswerActivity.this.finish();
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AnswerActivity.this);
                builder.setIcon(R.drawable.addclass)
                        .setTitle("提交答案")
                        .setMessage("确定提交？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //ToDo:数据传输：


                                AnswerActivity.this.finish();
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