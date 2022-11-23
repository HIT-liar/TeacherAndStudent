package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.data.ChoseQues;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;

public class ChosenActivity extends MyActivity {

    private ChoseQues choseQues = new ChoseQues(true) ;
    private EditText name;
    private EditText se_A;
    private EditText se_B;
    private EditText se_C;
    private EditText se_D;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen);

        ButtonReact();
    }

    private void ButtonReact() {

        name = (EditText) findViewById(R.id.chosen_ques_title_edit);
        se_A = (EditText) findViewById(R.id.a_ans);
        se_B = (EditText) findViewById(R.id.b_ans);
        se_C = (EditText) findViewById(R.id.c_ans);
        se_D = (EditText) findViewById(R.id.d_ans);

        radioGroup = (RadioGroup) findViewById(R.id.yes_abcd_group) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.chosen_add_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChosenActivity.this.finish();
            }
        });

        final boolean[] ansflag = {false};
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ansflag[0] = true;
                if(checkedId == R.id.a_yes){
                    choseQues.setAns("A");
                }else if(checkedId == R.id.b_yes){
                    choseQues.setAns("B");
                }else if(checkedId == R.id.c_yes){
                    choseQues.setAns("C");
                }else if(checkedId == R.id.d_yes){
                    choseQues.setAns("D");
                }
            }
        });

        Button button = (Button) findViewById(R.id.yes_chosen_send_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_str = name.getText().toString().trim();
                String a_str = se_A.getText().toString().trim();
                String b_str = se_B.getText().toString().trim();
                String c_str = se_C .getText().toString().trim();
                String d_str = se_D.getText().toString().trim();

                if(TextUtils.isEmpty(title_str)||TextUtils.isEmpty(a_str)||TextUtils.isEmpty(b_str)
                        ||TextUtils.isEmpty(c_str)||TextUtils.isEmpty(d_str)||ansflag[0] == false){
                    Toast.makeText(getApplicationContext(),"您输入的信息不完善",Toast.LENGTH_SHORT).show();
                }else {

                    choseQues.setQuestion(title_str);
                    choseQues.setA(a_str);
                    choseQues.setB(b_str);
                    choseQues.setC(c_str);
                    choseQues.setD(d_str);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ChosenActivity.this);
                    builder.setIcon(R.drawable.change)
                            .setTitle("发布习题")
                            .setMessage("确认发布？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCollector.finishOneActivity(ChosenActivity.class.getName());
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