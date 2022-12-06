package com.example.firstpro.activity.teactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.firstpro.fragment.AddfileFragment;
import com.example.firstpro.fragment.MySendFragmentPagerAdapter;
import com.example.firstpro.fragment.QuestionFragment;
import com.example.firstpro.R;

import java.util.ArrayList;

public class SendActivity extends AppCompatActivity implements  View.OnClickListener {

    ViewPager2 viewPager;
    private LinearLayout llfile,llquestion;
    private ImageView ivfile,ivquestion,ivcurrent;

    private String classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bun");
        classid = bundle.getString("classid");

        Toolbar toolbar = (Toolbar)findViewById(R.id.send_toolbar) ;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendActivity.this.finish();
            }
        });

        initPager();
        initTableView();
    }

    private void initTableView() {
        llfile = (LinearLayout) findViewById(R.id.id_tab_ppt_send);
        llfile.setOnClickListener(this);
        llquestion = (LinearLayout) findViewById(R.id.id_tab_qus_send);
        llquestion.setOnClickListener(this);

        ivfile = (ImageView) findViewById(R.id.tab_iv_ppt);
        ivquestion = (ImageView) findViewById(R.id.tab_iv_qus);

        ivfile.setSelected(true);
        ivcurrent =ivfile;
    }

    private void initPager() {
        viewPager = findViewById(R.id.send_viewPager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(AddfileFragment.newInstance(classid,"qq"));
        fragments.add(QuestionFragment.newInstance(classid,"qq"));
        MySendFragmentPagerAdapter pagerAdapter = new MySendFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }
        });

    }

    private void changeTab(int position) {
        ivcurrent.setSelected(false);
        switch (position){
            case R.id.id_tab_ppt_send:
                viewPager.setCurrentItem(0);
            case 0:
                ivfile.setSelected(true);
                ivcurrent =ivfile;
                break;
            case R.id.id_tab_qus_send:
                viewPager.setCurrentItem(1);
            case 1:
                ivquestion.setSelected(true);
                ivcurrent =ivquestion;
                break;

        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }
}