package com.example.firstpro.activity.teactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.commonactivity.PersonIformationActivity;

public class teacherMainPageAbility extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_page_ability);

        ButtonReact();
    }

    private void ButtonReact(){

        Button button_per = (Button) findViewById(R.id.personal_btn);

        Button button_classes = (Button) findViewById(R.id.myclasses_btn);

        Button button_beginclass = (Button) findViewById(R.id.classbegin_btn);

        button_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(teacherMainPageAbility.this, PersonIformationActivity.class);

                startActivity(intent);
            }
        });

        button_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(teacherMainPageAbility.this, myclassesActivity.class);

                startActivity(intent);
            }
        });

        button_beginclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(teacherMainPageAbility.this, OnClassActivity.class);

                startActivity(intent);
            }
        });
    }


}