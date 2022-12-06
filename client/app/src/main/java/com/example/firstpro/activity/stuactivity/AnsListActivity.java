package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.listviewadapter.AnswerListAdapter;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.activity.listviewadapter.FileListAdapter;
import com.example.firstpro.activity.listviewadapter.StuAnsListAdapter;
import com.example.firstpro.activity.teactivity.SendActivity;
import com.example.firstpro.data.Answer;
import com.example.firstpro.data.Class;
import com.example.firstpro.data.Question;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AnsListActivity extends AppCompatActivity {

    private List<Question> list = new ArrayList<Question>();
    private ListView listView;

    private StuAnsListAdapter ansListAdapter;
    private Context context;

    private ServerService sv =new ServerService();
    private MyHandler myHandler = new MyHandler(this);
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans_list);
        context = this;
        classId = getIntent().getBundleExtra("bun").getString("classid");

        ButtonReact();
        ListReact();

    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.stu_all_ques_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnsListActivity.this.finish();
            }
        });
    }

    private void ListReact(){
        listView= (ListView) findViewById(R.id.stu_ques_act_listview);

        getQuesFromRemote();
    }

    private void getQuesFromRemote(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = sv.getQuestion(classId, MyURL.QAURL);
                Message msg = myHandler.obtainMessage();
                msg.obj = questions;
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<AnsListActivity> weakReference;

        public MyHandler(AnsListActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Question> que = (List<Question>) msg.obj;
            switch (msg.what) {
                case 1:
                    list.clear();
                    list.addAll(que);
                    ansListAdapter = new StuAnsListAdapter(context,list);
                    listView.setAdapter(ansListAdapter);
                    break;
            }
        }
    }

}