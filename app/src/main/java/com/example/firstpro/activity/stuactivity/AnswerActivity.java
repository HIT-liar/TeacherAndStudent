package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.MyActivity;
import com.example.firstpro.activity.listviewadapter.ClassListAdapter;
import com.example.firstpro.data.Answer;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnswerActivity extends MyActivity {

    private String questext;
    private int quesId;
    private String stuId = AutoLoginStatic.getInstance().getUserNum(this);
    private ServerService sv = new ServerService();
    private MyHandler myHandler = new MyHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        questext = bundle.getString("quesText");
        quesId = bundle.getInt("quesId");
        System.out.println("真正的quesid"+quesId);
        ButtonReact();
    }

    private void ButtonReact(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.answerQuestion_toolbar);

        Button button_yes = (Button) findViewById(R.id.button_stu_submit_answer);

        TextView view = (TextView)  findViewById(R.id.question_textview) ;

        TextInputEditText text = (TextInputEditText) findViewById(R.id.answerInput) ;

        view.setText(questext);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AnswerActivity.this.finish();
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AnswerActivity.this);
                builder.setIcon(R.drawable.addclass)
                        .setTitle("提交答案")
                        .setMessage("确定提交？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //ToDo:数据传输：
                         String ans_text = text.getText().toString();
                         if (ans_text == null||ans_text.equals("")){
                             Toast.makeText(getApplicationContext(),"答案不能为空",Toast.LENGTH_SHORT).show();
                         }else{
                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                             String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                             Answer ans_ = new Answer(stuId,quesId,ans_text,date);
                             SendAnsToRemote(ans_);
                         }
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
        });
    }

    private void SendAnsToRemote(Answer ans){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = sv.SendAnswer(ans, MyURL.QAURL);
                Message msg =myHandler.obtainMessage();
                if(flag){
                    msg.arg1 =1 ;
                }else{
                    msg.arg1 = 2;
                }
                msg.what =1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<AnswerActivity> weakReference;

        public MyHandler(AnswerActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                  if(msg.arg1 == 1){
                      Toast.makeText(getApplicationContext(),"回答成功",Toast.LENGTH_SHORT).show();
                      AnswerActivity.this.finish();
                  }else {
                      Toast.makeText(getApplicationContext(),"添加回答失败",Toast.LENGTH_SHORT).show();
                  }
                    break;
            }
        }
    }
}