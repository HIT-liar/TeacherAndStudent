package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.data.ChoseQues;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;

public class TextQuesActivity extends MyActivity {


    private ChoseQues choseQues = new ChoseQues(false);

    EditText editText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_ques);

        ButtonReact();
    }

    private void ButtonReact() {

        editText = (EditText) findViewById(R.id.text_ques_title_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.textques_add_toolbar);
        Button button = (Button) findViewById(R.id.yes_text_send_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextQuesActivity.this.finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_str = editText.getText().toString().trim();

                if(TextUtils.isEmpty(title_str)){
                    Toast.makeText(getApplicationContext(),"还未输入题目",Toast.LENGTH_SHORT).show();
                }else{
                    choseQues.setQuestion(title_str);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TextQuesActivity.this);
                    builder.setIcon(R.drawable.change)
                            .setTitle("发布习题")
                            .setMessage("确认发布？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    ActivityCollector.finishOneActivity(TextQuesActivity.class.getName());
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