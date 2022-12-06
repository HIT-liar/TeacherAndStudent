package com.example.firstpro.activity.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.data.Student;

import java.util.List;

public class StuListAdapter extends BaseAdapter {

    private List<Student> list;
    private Context context;
    private LayoutInflater mInflater;

    public StuListAdapter(Context context,List<Student> list){

        this.list=list;
        this.context=context;
        mInflater=LayoutInflater.from(context);
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
        View stu = convertView;
        ViewHolder viewHolder;
        if(stu==null){
            stu = mInflater.inflate(R.layout.student_list,null);
            viewHolder = new ViewHolder();
            viewHolder.StuGender = (TextView) stu.findViewById(R.id.stu_gender_list);
            viewHolder.StuName = (TextView) stu.findViewById(R.id.stu_name_list);
            viewHolder.StuNum =(TextView) stu.findViewById(R.id.stu_num_list);
            stu.setTag(viewHolder);

    }else {
            viewHolder = (ViewHolder) stu.getTag();
        }

        try {
            if(!list.get(position).getStu_name().equals("")){
                viewHolder.StuName.setText(list.get(position).getStu_name());
            }else {
                viewHolder.StuName.setText("姓名不详");
            }
            if(!list.get(position).getStu_num().equals("")){
                viewHolder.StuNum.setText(list.get(position).getStu_num());
            }else {
                viewHolder.StuNum.setText("学号不详");
            }
            if (list.get(position).getStu_gender()){
                viewHolder.StuGender.setText("男");
            }else {
                viewHolder.StuGender.setText("女");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stu;
    }

    public final class ViewHolder{
        public TextView StuName;
        public TextView StuNum;
        public TextView StuGender;

    }
}
