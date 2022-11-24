package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;

public class StuOnClassActivity extends MyActivity {

    boolean isHandsUp = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_on_class);

        ButtonReact();
    }

    private void ButtonReact(){

        Button button_sign = (Button) findViewById(R.id.stu_button_sign);
        Button button_file = (Button) findViewById(R.id.stu_button_file);
        Button button_answer = (Button) findViewById(R.id.stu_button_answer);
        Button button_handsup= (Button) findViewById(R.id.stu_button_handsup);
        Button button_leave= (Button) findViewById(R.id.stu_button_leaveclass);

        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //StuOnClassActivity.this.finish();
            }
        });

        button_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //ToDo：获得文件

            }
        });

        button_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(StuOnClassActivity.this, AnswerActivity.class);

                startActivity(intent);
            }
        });

        button_handsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(StuOnClassActivity.this);
                if(!isHandsUp) {
                    isHandsUp = true;
                    builder.setIcon(R.drawable.hand)
                            .setMessage("已经向老师举手")
                            .create()
                            .show();
                    button_handsup.setBackgroundResource(R.drawable.button7);

                }else{
                    isHandsUp = false;
                    builder.setIcon(R.drawable.hand)
                            .setMessage("已经取消举手")
                            .create()
                            .show();
                    button_handsup.setBackgroundResource(R.drawable.button6);
                }

            }
        });
        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuOnClassActivity.this.finish();
            }
        });
    }
}