package com.example.firstpro.activity.activityhelper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstpro.activity.activityhelper.ActivityCollector;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在活动管理器添加当前Activity
        ActivityCollector.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //从活动管理器删除当前Activity
        ActivityCollector.removeActivity(this);
    }


}
