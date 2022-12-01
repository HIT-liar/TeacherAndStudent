package com.example.firstpro.activity.teactivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.listviewadapter.StuListAdapter;
import com.example.firstpro.data.Class;
import com.example.firstpro.data.Student;
import com.example.firstpro.database.ClassesSQLIteHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ChosenClassStudentsActivity extends MyActivity {

    private List<Student> list = new ArrayList<Student>();
    private ListView listView;
    private String classid="";

    private StuListAdapter stuListAdapter;

    private MyHandler myHandler= new MyHandler(this);
    private ServerService sv = new ServerService();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_class_students);
        context=this;

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        classid = bundle.getString("classid");

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

        //从远端数据库获取数据
        //ToDo:获取选了该课的学生信息
        GetStuFromRemote();

        //ToChange:
//        student1.setStu_gender(true);
//        student1.setStu_name("王五");
//        student1.setStu_num("120L028888");
//        list.add(student1);
//
//        student2.setStu_gender(false);
//        student2.setStu_name("刘美美");
//        student2.setStu_num("120L028882");
//        list.add(student2);
//
//        stuListAdapter = new StuListAdapter(this,list);
//        listView.setAdapter(stuListAdapter);
    }

    private void GetStuFromRemote(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = sv.StuChosenClass(classid, MyURL.TeaURL);
                Message msg = myHandler.obtainMessage();
                msg.what=1;
                msg.obj=list;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<ChosenClassStudentsActivity> weakReference;

        public MyHandler(ChosenClassStudentsActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            List<Student> students;
            switch (msg.what) {
                case 1:
                    students = (List<Student>) msg.obj;
                    stuListAdapter = new StuListAdapter(context,students);
                    listView.setAdapter(stuListAdapter);
                    break;
            }
        }
    }
}