package com.example.firstpro.activity.stuactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.firstpro.R;
import com.example.firstpro.activity.activityhelper.MyActivity;

public class SignInActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_sign_in);

        ButtonReact();
    }

    private void ButtonReact(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.stu_sign_toolbar);

        Button button_start = (Button) findViewById(R.id.startsign_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInActivity.this.finish();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(SignInActivity.this);
                builder.setIcon(R.drawable.ic_baseline_how_to_reg_24)
                        .setMessage("签到成功")

                        .create()
                        .show();
                SignInActivity.this.finish();
            }
        });
    }
}