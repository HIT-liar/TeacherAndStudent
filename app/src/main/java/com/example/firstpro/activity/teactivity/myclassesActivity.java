package com.example.firstpro.activity.teactivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.data.Class;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.database.ChooseClassSQLIteHelper;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.R;
import com.example.firstpro.helper.NetJudgeHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class myclassesActivity extends MyActivity {

    private  List<Class> list = new ArrayList<Class>();
    private ListView listView;

    private ClassListAdapter classListAdapter;
    private Context context;

    private ServerService sv = new ServerService();
    private MyHandler myHandler = new MyHandler(this);

    private String Teaid = AutoLoginStatic.getInstance().getUserNum(this);

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
        class1.setClass_id("12E3");
        class1.setTrue_stu_num(50);
        class1.setClass_num(60);
        class1.setMax_stu_num(100);
        class1.setCourse_credit(5.5);
        class1.setLocation_Of_Class("正心11");
        class1.setTime("周一 8:00-9:45，周五 8:00-9:45");
        list.add(class1);

        class2.setClass_name("计算机组成原理");
        class2.setClass_id("23R4");
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
                bundle.putString("class_id",list.get(position).getClass_id());
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

        //先判断是否联网，联网的话就取远端数据，显示并更新本地数据库
        //ToDo:取远端数据
        boolean find_in_local = true;
        //只要联网了，就取远端数据并更新本地数据库
        if(NetJudgeHelper.isNetworkAvailable(context)){
            find_in_local=false;//就不用本地设置了
            MyClassesFromRemote();
        }
        //如果断网，则直接显示本地数据
        if(find_in_local) {
            ClassesSQLIteHelper helper = ClassesSQLIteHelper.getInstance(context);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select classid,teacheraccount,classname,classnum,credit,location,maxstunum,time,true_stunum from classes";
            Cursor cursor = helper.query(sql, null);
            int size = cursor.getCount();

            String account = AutoLoginStatic.getInstance().getUserNum(context);

            int classid_index = cursor.getColumnIndex("classid");
            int teacheracc_index = cursor.getColumnIndex("teacheraccount");
            int classname_index = cursor.getColumnIndex("classname");
            int classnum_index = cursor.getColumnIndex("classnum");
            int credit_index = cursor.getColumnIndex("credit");
            int location_index = cursor.getColumnIndex("location");
            int max_index = cursor.getColumnIndex("maxstunum");
            int time_index = cursor.getColumnIndex("time");
            int true_index = cursor.getColumnIndex("true_stunum");

            for (int i = 0; i < size; i++) {
                cursor.moveToNext();
                if (cursor.getString(teacheracc_index).equals(account)) {
                    Class myclass = new Class();
                    myclass.setClass_id(cursor.getString(classid_index));
                    myclass.setClass_name(cursor.getString(classname_index));
                    myclass.setTrue_stu_num(cursor.getInt(true_index));
                    myclass.setClass_num(cursor.getInt(classnum_index));
                    myclass.setMax_stu_num(cursor.getInt(max_index));
                    myclass.setCourse_credit(cursor.getDouble(credit_index));
                    myclass.setLocation_Of_Class(cursor.getString(location_index));
                    myclass.setTime(cursor.getString(time_index));

                    list.add(myclass);
                }
            }
            cursor.close();
            db.close();
        }
    }

    public void MyClassesFromRemote(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Class> classList = sv.getClasses(MyURL.TeaURL,true,Teaid,false,null);
                Message msg =myHandler.obtainMessage();
                msg.obj = classList;
                msg.what = 1;

                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<myclassesActivity> weakReference;

        public MyHandler(myclassesActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Class> classes = (List<Class>) msg.obj;
            switch (msg.what) {
                case 1:
                    list.addAll(classes);
                    classListAdapter = new ClassListAdapter(context,list);
                    listView.setAdapter(classListAdapter);
                    ClassesSQLIteHelper.getInstance(context).update(list,context);
                    break;
            }
        }
    }

}