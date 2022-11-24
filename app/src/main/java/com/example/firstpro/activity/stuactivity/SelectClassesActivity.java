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

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.stuclasshelper.ClassInfoAdapter;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SelectClassesActivity extends MyActivity {

    Toolbar toolbar;
//    FloatingActionButton pseudo_addClass;

    List<Class> selectedClass;
    ClassInfoAdapter classInfoAdapter;
    RecyclerView recyclerView;
    Context context ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classes);
        context = this;

        toolbar = findViewById(R.id.selectclasses_toolbar);

        recyclerView = findViewById(R.id.selected_classes);
        toolbar.setNavigationOnClickListener(view -> {
            SelectClassesActivity.this.finish();
        });

        //ToDo:从远端数据库拉取全部课程

        selectedClass = new ArrayList<>();

        classInfoAdapter = new ClassInfoAdapter(context,
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }},
                true,
                 selectedClass);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setAdapter(classInfoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectClassesActivity.this));

    }


    class MyDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,1);
        }
    }
}