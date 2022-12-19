package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;

public class answerDetailActivity extends MyActivity {

    private String ansText;
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);

        Intent intent =getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        ansText = bundle.getString("ansText");
        time = bundle.getString("time");

        TextSet();
    }

    private void TextSet(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.ans_detail_toolbar) ;
        TextView ans = (TextView) findViewById(R.id.the_ans_text);
        TextView _time = (TextView) findViewById(R.id.ans_time_text);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerDetailActivity.this.finish();
            }
        });

        ans.setText(ansText);
        _time.setText(time);
    }


}