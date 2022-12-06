package com.example.firstpro.activity.listviewadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.activity.stuactivity.AnswerActivity;
import com.example.firstpro.activity.teactivity.StuIsSignActivity;
import com.example.firstpro.data.Answer;
import com.example.firstpro.data.Question;

import java.util.List;

public class StuAnsListAdapter extends BaseAdapter {
    private List<Question> list;
    private Context context;
    private LayoutInflater mInflater;

    public StuAnsListAdapter(Context context , List<Question> list){
        this.context = context;
        this.list =list;
        mInflater  =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ans_ = convertView;
        ViewHolder viewHolder;
        if(ans_ == null){
            ans_ = mInflater.inflate(R.layout.ques_item , null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) ans_.findViewById(R.id.stu_ques_time);
            viewHolder.ques = (TextView) ans_.findViewById(R.id.stu_ques_text);
            viewHolder.allans = (Button) ans_.findViewById(R.id.all_ans_btn) ;
            viewHolder.myans = (Button)ans_.findViewById(R.id.to_ans_btn) ;
            ans_.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) ans_.getTag();
        }
        try {
            if(list.get(position).getText()!=null){
                viewHolder.ques.setText(list.get(position).getText());
            }else {
                viewHolder.ques.setText("题目获取失败");
            }

            if(list.get(position).getDate()!=null){
                viewHolder.date.setText(list.get(position).getDate());
            }else {
                viewHolder.date.setText("时间不详");
            }

         viewHolder.allans.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 Bundle bundle =new Bundle();
                 bundle.putInt("quesID",list.get(position).GetQuestionId());
                 bundle.putString("quesText",list.get(position).getText());
                 intent.putExtra("bun",bundle);
                 intent.setClass(context, StuIsSignActivity.class);

                 context.startActivity(intent);

             }
         });

            viewHolder.myans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle =new Bundle();
                    bundle.putString("quesText",list.get(position).getText());
                    bundle.putInt("quesId",list.get(position).GetQuestionId());
                    System.out.println("我来回答的quesid"+list.get(position).GetQuestionId());
                    intent.putExtra("bun",bundle);
                    intent.setClass(context, AnswerActivity.class);

                    context.startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return ans_;
    }

    public final class ViewHolder{
        public TextView date;
        public TextView ques;
        public Button allans;
        public Button myans;
    }
}
