package com.example.firstpro.activity.teactivity;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.listviewadapter.StuListAdapter;
import com.example.firstpro.data.Student;

import java.util.ArrayList;
import java.util.List;

public class ChosenClassStudentsActivity extends MyActivity {

    private List<Student> list = new ArrayList<Student>();
    private ListView listView;

    private StuListAdapter stuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_class_students);


        ButtonReact();
        ListReact();

    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.chosen_stulist_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChosenClassStudentsActivity.this.finish();
            }
        });
    }

    private void ListReact(){
        listView = (ListView) findViewById(R.id.chosenstu_listview);
        Student student1 = new Student();
        Student student2 = new Student();

        student1.setStu_gender(true);
        student1.setStu_name("王五");
        student1.setStu_num("120L028888");
        list.add(student1);

        student2.setStu_gender(false);
        student2.setStu_name("刘美美");
        student2.setStu_num("120L028882");
        list.add(student2);

        stuListAdapter = new StuListAdapter(this,list);
        listView.setAdapter(stuListAdapter);
    }

}