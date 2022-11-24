package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.commonactivity.PersonIformationActivity;

public class StuMainPageActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_main_page);

        ButtonReact();
    }

    private void ButtonReact(){

        Button button_per = (Button) findViewById(R.id.stu_personal_btn);

        Button button_classes = (Button) findViewById(R.id.stu_myclasses_btn);

        Button button_beginclass = (Button) findViewById(R.id.stu_classbegin_btn);

        button_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(StuMainPageActivity.this, PersonIformationActivity.class);

                startActivity(intent);
            }
        });

        button_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(StuMainPageActivity.this, MyClassesActivity.class);

                startActivity(intent);
            }
        });

        button_beginclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(StuMainPageActivity.this,StuOnClassActivity.class);

                startActivity(intent);
            }
        });
    }
}