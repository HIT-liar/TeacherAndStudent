package com.example.firstpro.activity.stuactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;

import java.util.Set;

public class StuMyclassMessageActivity extends MyActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_myclass_message);

        ButtonReact();
        SetText();

    }

    private void ButtonReact() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.stu_c_m_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StuMyclassMessageActivity.this.finish();
            }
        });
    }
    private void SetText() {

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");

        TextView class_id = (TextView) findViewById(R.id.stu_class_class_id_message) ;
        class_id .setText(bundle.getString("class_id"));

        TextView classname = (TextView) findViewById(R.id.stu_class_name_message);
        classname.setText(bundle.getString("classname"));

        TextView classnum = (TextView) findViewById(R.id.stu_class_num_message);
        classnum.setText(bundle.getString("class_num"));

        TextView class_max_stu = (TextView) findViewById(R.id.stu_class_maxstu_message);
        class_max_stu.setText(bundle.getString("max_num"));

        TextView class_true_stu = (TextView) findViewById(R.id.stu_class_stunum_message);
        class_true_stu.setText(bundle.getString("true_num"));

        TextView credit = (TextView) findViewById((R.id.stu_class_credit_message));
        credit.setText(bundle.getString("credit"));

        TextView location = (TextView) findViewById((R.id.stu_class_location_message));
        location.setText(bundle.getString("location"));

        TextView time = (TextView) findViewById((R.id.stu_class_time_message));
        time.setText(bundle.getString("time"));

        TextView teachername = (TextView) findViewById(R.id.stu_class_teacher_message);
        teachername.setText(bundle.getString("teachername"));

    }
}