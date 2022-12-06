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
import android.widget.Button;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.stuclasshelper.ClassInfoAdapter;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.database.ChooseClassSQLIteHelper;
import com.example.firstpro.helper.NetJudgeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyClassesActivity extends MyActivity {
    private RecyclerView mRvMain;
    private List<Class> classList = new ArrayList<>();
    ChooseClassSQLIteHelper chooseClassSQLIteHelper;
    private Context context;

    private ServerService sv = new ServerService();
    MyHandler myHandler = new MyHandler(this);
    private String stu_account = AutoLoginStatic.getInstance().getUserNum(context);


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
        boolean find_in_local = true;
        //只要联网了，就取远端数据并更新本地数据库
        if(NetJudgeHelper.isNetworkAvailable(context)){
            find_in_local=false;//就不用本地设置了
            GetMyClass();
        }
        if(find_in_local) {
            chooseClassSQLIteHelper = ChooseClassSQLIteHelper.getInstance(context);
            classList = (ArrayList<Class>) chooseClassSQLIteHelper.getAll(stu_account, context);
            mRvMain.setAdapter(new ClassInfoAdapter(context, false, classList));
        }
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

    private void GetMyClass()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Class> _classList = sv.getClasses(MyURL.StuURL,false,null,true,stu_account);
                Message msg = myHandler.obtainMessage();
                msg.obj=_classList;
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<MyClassesActivity> weakReference;

        public MyHandler(MyClassesActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Class> classes = (List<Class>) msg.obj;
            switch (msg.what) {
                case 1:
                    ChooseClassSQLIteHelper.getInstance(context).update(classes,stu_account,context);
                    classList.clear();
                    classList.addAll(classes);
                    mRvMain.setAdapter(new ClassInfoAdapter(context,false, classList));
                    break;

            }
        }
    }
}