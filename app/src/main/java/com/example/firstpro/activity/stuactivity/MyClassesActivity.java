package com.example.firstpro.activity.stuactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.stuclasshelper.ClassInfoAdapter;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.database.ChooseClassSQLIteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyClassesActivity extends MyActivity {
    private RecyclerView mRvMain;
    private List<Class> classList;
    ChooseClassSQLIteHelper chooseClassSQLIteHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_my_classes);
        context = this;

        ButtonReact();
        mRvMain = findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(MyClassesActivity.this));
        mRvMain.addItemDecoration(new MyDecoration());

        viewAll();
    }

    private void viewAll() {
        String stu_account = AutoLoginStatic.getInstance().getUserNum(context);
        chooseClassSQLIteHelper = ChooseClassSQLIteHelper.getInstance(context);
        classList = (ArrayList<Class>) chooseClassSQLIteHelper.getAll(stu_account,context);
        mRvMain.setAdapter(new ClassInfoAdapter(context,false, classList));
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.stu_myclasses_toolbar);
        Button button_add = (Button) findViewById(R.id.stu_addclass_btn);
        FloatingActionButton button_renew = findViewById(R.id.renew_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyClassesActivity.this, StuMainPageActivity.class);
                startActivity(intent);

                ActivityCollector.finishOneActivity(MyClassesActivity.class.getName());
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyClassesActivity.this, SelectClassesActivity.class);
                startActivity(intent);
            }
        });
        button_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAll();
            }
        });
    }

    class MyDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,1);
        }
    }
}