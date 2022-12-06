package com.example.firstpro.activity.stuactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.activity.stuclasshelper.ClassInfoAdapter;
import com.example.firstpro.activity.teactivity.myclassesActivity;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SelectClassesActivity extends MyActivity {

    private Toolbar toolbar;
//    FloatingActionButton pseudo_addClass;

    private List<Class> selectedClass= new ArrayList<>();;
    private ClassInfoAdapter classInfoAdapter;
    private RecyclerView recyclerView;
    private Context context ;

    private String stu_id = AutoLoginStatic.getInstance().getUserNum(this);

    private MyHandler myHandler = new MyHandler(this);
    private ServerService sv = new ServerService();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classes);
        context = this;

        toolbar = findViewById(R.id.selectclasses_toolbar);

        recyclerView = findViewById(R.id.selected_classes);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectClassesActivity.this));
        recyclerView.addItemDecoration(new MyDecoration());
        toolbar.setNavigationOnClickListener(view -> {
            SelectClassesActivity.this.finish();
        });

        //ToDo:从远端数据库拉取全部课程
        GetAllClasses();

//        classInfoAdapter = new ClassInfoAdapter(context,
////                new Button.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////
////                    }},
//                true,
//                 selectedClass);
//        recyclerView.addItemDecoration(new MyDecoration());
//        recyclerView.setAdapter(classInfoAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(SelectClassesActivity.this));

    }


    class MyDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,1);
        }
    }

    public void GetAllClasses(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Class> classList = sv.getClasses(MyURL.StuURL,false,null,false,stu_id);
                System.out.println(classList.size());
                Message msg = myHandler.obtainMessage();
                msg.obj = classList;
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }


    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<SelectClassesActivity> weakReference;

        public MyHandler(SelectClassesActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Class> classes = (List<Class>) msg.obj;
            switch (msg.what) {
                case 1:
                    selectedClass.clear();
                    selectedClass.addAll(classes);
                    classInfoAdapter = new ClassInfoAdapter(context, true, selectedClass);
                    recyclerView.setAdapter(classInfoAdapter);
                    break;

            }
        }
    }
}