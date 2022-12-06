package com.example.firstpro.activity.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.data.Class;

import java.util.List;

public class ClassListAdapter extends BaseAdapter {

    private List<Class> list;
    private Context context;
    private LayoutInflater mInflater;

    public ClassListAdapter(Context context,List<Class> list){

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
    public View getView( final int position, View convertView, ViewGroup parent) {
        View Class = convertView;
        ViewHolder viewHolder;
        if(Class==null){
            Class = mInflater.inflate(R.layout.class_item,null);
            viewHolder = new ViewHolder();
            viewHolder.ClassName =(TextView) Class.findViewById(R.id.classname_text);
            viewHolder.Location =(TextView) Class.findViewById(R.id.location);
            viewHolder.TrueNum = (TextView) Class.findViewById(R.id.true_num);
            viewHolder.Time = (TextView) Class.findViewById(R.id.time_text);
            Class.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) Class.getTag();
        }
        try {
            if(!list.get(position).getClass_name().equals("")){
                viewHolder.ClassName.setText(list.get(position).getClass_name());
            }else{
                viewHolder.ClassName.setText("未知课程");
            }

            if(list.get(position).getTrue_stu_num()>=0){
                viewHolder.TrueNum.setText(new Integer(list.get(position).getTrue_stu_num()).toString());
            }else{
                viewHolder.TrueNum.setText("0");
            }

            if(!list.get(position).getLocation_Of_Class().equals("")){
                viewHolder.Location.setText(list.get(position).getLocation_Of_Class());
            }else{
                viewHolder.Location.setText("上课地点不详");
            }

            if(!list.get(position).getTime().equals("")){
                viewHolder.Time.setText(list.get(position).getTime());
            }else{
                viewHolder.Time.setText("上课时间不详");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Class;
    }


    public final class ViewHolder{
        public TextView ClassName;
        public TextView TrueNum;
        public TextView Location;
        public TextView Time;
    }
}
