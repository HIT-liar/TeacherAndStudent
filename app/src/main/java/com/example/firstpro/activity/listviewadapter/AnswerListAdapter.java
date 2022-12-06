package com.example.firstpro.activity.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.data.Answer;


import java.util.List;

public class AnswerListAdapter extends BaseAdapter {
    private List<Answer> list;
    private Context context;
    private LayoutInflater mInflater;

    public AnswerListAdapter(Context context , List<Answer> list){
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
       AnswerListAdapter.ViewHolder viewHolder;
        if(ans_ == null){
            ans_ = mInflater.inflate(R.layout.answer_item , null);
            viewHolder = new AnswerListAdapter.ViewHolder();
            viewHolder.date = (TextView) ans_.findViewById(R.id.ans_time_list);
            viewHolder.stuName = (TextView) ans_.findViewById(R.id.ans_stu_name_list);
            viewHolder.stuNum = (TextView) ans_.findViewById(R.id.ans_stu_num_list) ;
            ans_.setTag(viewHolder);
        }else {
            viewHolder = (AnswerListAdapter.ViewHolder) ans_.getTag();
        }
        try {
            if(list.get(position).getStuName()!=null){
                viewHolder.stuName.setText(list.get(position).getStuName());
            }else {
                viewHolder.stuName.setText("姓名不详");
            }

            if(list.get(position).getTime()!=null){
                viewHolder.date.setText(list.get(position).getTime());
            }else {
                viewHolder.date.setText("时间不详");
            }

            if(list.get(position).getStuId()!=null){
                viewHolder.stuNum.setText(list.get(position).getStuId());
            }else{
                viewHolder.stuNum.setText("学号不详");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ans_;
    }

    public final class ViewHolder{
        public TextView date;
        public TextView stuName;
        public TextView stuNum;
    }
}
