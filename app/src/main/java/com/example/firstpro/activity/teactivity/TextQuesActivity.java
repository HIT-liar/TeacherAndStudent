package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.data.ChoseQues;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.R;
import com.example.firstpro.data.Class;
import com.example.firstpro.data.Question;
import com.example.firstpro.database.ClassesSQLIteHelper;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TextQuesActivity extends MyActivity {


    private ChoseQues choseQues = new ChoseQues(false);

    private EditText editText ;
    private String classId;

    private ServerService sv = new ServerService();
    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_ques);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        classId = bundle.getString("classsid");

        ButtonReact();
    }

    private void ButtonReact() {

        editText = (EditText) findViewById(R.id.text_ques_title_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.textques_add_toolbar);
        Button button = (Button) findViewById(R.id.yes_text_send_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextQuesActivity.this.finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_str = editText.getText().toString().trim();

                if(TextUtils.isEmpty(title_str)){
                    Toast.makeText(getApplicationContext(),"还未输入题目",Toast.LENGTH_SHORT).show();
                }else{
                    choseQues.setQuestion(title_str);
                    AlertDialog.Builder builder = new AlertDialog.Builder(TextQuesActivity.this);
                    builder.setIcon(R.drawable.change)
                            .setTitle("发布习题")
                            .setMessage("确认发布？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                                    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                                    Question ques = new Question(classId,title_str,date);
                                    AddQuesToRemote(ques);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ;
                                }
                            })
                            .create()
                            .show();
                }
            }
        });
    }

    public void AddQuesToRemote(Question q){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = sv.SendQues(q, MyURL.QAURL);
                Message msg = myHandler.obtainMessage();
                if(flag){
                    msg.arg1 =1;
                }else{
                    msg.arg1 =2;
                }
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<TextQuesActivity> weakReference;

        public MyHandler(TextQuesActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    if(msg.arg1 == 1){
                        Toast.makeText(getApplicationContext(),"添加问题成功",Toast.LENGTH_SHORT).show();
                        ActivityCollector.finishOneActivity(TextQuesActivity.class.getName());
                    }else{
                        Toast.makeText(getApplicationContext(),"添加问题失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}