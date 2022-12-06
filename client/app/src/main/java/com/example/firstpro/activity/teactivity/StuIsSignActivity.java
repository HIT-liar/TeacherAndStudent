package com.example.firstpro.activity.teactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.activity.listviewadapter.AnswerListAdapter;
import com.example.firstpro.activity.listviewadapter.FileListAdapter;
import com.example.firstpro.activity.listviewadapter.StuListAdapter;
import com.example.firstpro.data.Answer;
import com.example.firstpro.data.Question;
import com.example.firstpro.data.Student;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StuIsSignActivity extends MyActivity {

    private List<Answer> list = new ArrayList<Answer>();
    private ListView listView;

    private AnswerListAdapter answerListAdapter;
    private int quesId;
    private String questext;

    private ServerService sv = new ServerService();
    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_is_sign);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        questext = bundle.getString("quesText");
        quesId = bundle.getInt("quesID");

        TextView textView = (TextView) findViewById(R.id.ques_text);
        textView.setText(questext);


        ListReact();
        ButtonReact();
    }

    private void ListReact() {

        //ToChange:从远程调取该题答案
        listView = (ListView) findViewById(R.id.stu_is_sign);
        getAnswersFromRemote();
//         Answer a = new Answer("11111",22,"2","2022-2-2 11:11:11");
//         a.setStuName("haha");
//         a.setAnswerId(23);
//         list.add(a);
//        answerListAdapter = new AnswerListAdapter(this,list);
//        listView.setAdapter(answerListAdapter);

    }

    private void ButtonReact() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.stu_is_sign_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StuIsSignActivity.this.finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("ansText",list.get(position).getAnsText());
                bundle.putString("time",list.get(position).getTime());
                intent.putExtra("bun",bundle);
                intent.setClass(StuIsSignActivity.this,answerDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getAnswersFromRemote(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Answer> answers = sv.getAnswer(quesId, MyURL.QAURL);
                Message msg = myHandler.obtainMessage();
                msg.obj = answers;
                msg.what =1 ;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<StuIsSignActivity> weakReference;

        public MyHandler(StuIsSignActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Answer> anss  =(List<Answer>) msg.obj;
            switch (msg.what) {
                case 1:
                    list.clear();
                    list.addAll(anss);
                    answerListAdapter = new AnswerListAdapter(getApplicationContext(),list);
                    listView.setAdapter(answerListAdapter);
                    break;
            }
        }
    }
}