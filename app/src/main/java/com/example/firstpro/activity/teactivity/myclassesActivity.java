package com.example.firstpro.activity.teactivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.data.Class;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;

import java.util.ArrayList;
import java.util.List;


public class myclassesActivity extends MyActivity {

    private List<Class> list = new ArrayList<Class>();
    private ListView listView;

    private ClassListAdapter classListAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclasses);
        context=this;

        ButtonReact();
        ListReact();
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.myclasses_toolbar);
        Button button_add = (Button) findViewById(R.id.addclass_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishOneActivity(myclassesActivity.class.getName());

            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(myclassesActivity.this, AddClassesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ListReact(){
        listView = (ListView) findViewById(R.id.class_listview);
        Class class1 =new Class();
        Class class2 =new Class();

        class1.setClass_name("微积分");
        class1.setTrue_stu_num(50);
        class1.setClass_num(60);
        class1.setMax_stu_num(100);
        class1.setCourse_credit(5.5);
        class1.setLocation_Of_Class("正心11");
        class1.setTime("周一 8:00-9:45，周五 8:00-9:45");
        list.add(class1);

        class2.setClass_name("计算机组成原理");
        class2.setTrue_stu_num(50);
        class2.setClass_num(48);
        class2.setMax_stu_num(100);
        class2.setCourse_credit(4.5);
        class2.setLocation_Of_Class("正心511");
        class2.setTime("周二 8:00-9:45，周三 8:00-9:45");
        list.add(class2);

        getClassFromDB();

        classListAdapter = new ClassListAdapter(this,list);
        listView.setAdapter(classListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(myclassesActivity.this, ClassMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",list.get(position).getClass_name());
                bundle.putString("num", new Integer(list.get(position).getClass_num()).toString());
                bundle.putString("stu_max",new Integer(list.get(position).getMax_stu_num()).toString());
                bundle.putString("stu_true",new Integer(list.get(position).getTrue_stu_num()).toString());
                bundle.putString("credit",new Double(list.get(position).getCourse_credit()).toString());
                bundle.putString("location",list.get(position).getLocation_Of_Class());
                bundle.putString("time",list.get(position).getTime());
                intent.putExtra("bun",bundle);
                startActivity(intent);
            }
        });

    }

    private void getClassFromDB(){
         MySQLIteHelper helper = MySQLIteHelper.getInstance(context);
         String sql = "select teacheraccount,classname,classnum,credit,location,maxstunum,time from classes";
        Cursor cursor = helper.query(sql,null);
        int size = cursor.getCount();

        String account = AutoLoginStatic.getInstance().getUserNum(context);

        for(int i=0;i<size;i++){
            cursor.moveToNext();
            if(cursor.getString(0).equals(account)){
                Class myclass = new Class();
                myclass.setClass_name(cursor.getString(1));
                myclass.setTrue_stu_num(50);
                myclass.setClass_num(cursor.getInt(2));
                myclass.setMax_stu_num(cursor.getInt(5));
                myclass.setCourse_credit(cursor.getDouble(3));
                myclass.setLocation_Of_Class(cursor.getString(4));
                myclass.setTime(cursor.getString(6));

                list.add(myclass);
            }
        }

    }

}