package com.example.firstpro.activity.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstpro.R;
import com.example.firstpro.data.FileToSend;
import com.example.firstpro.data.Question;

import java.util.List;

public class FileListAdapter extends BaseAdapter {

    private List<Question> list;
    private Context context;
    private LayoutInflater mInflater;

    public FileListAdapter(Context context, List<Question>list){

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
       View file_send = convertView;
       ViewHolder viewHolder;
       if(file_send == null){
           file_send = mInflater.inflate(R.layout.file_item , null);
           viewHolder = new ViewHolder();
           viewHolder.date = (TextView) file_send.findViewById(R.id.ques_time);
           viewHolder.FileName = (TextView) file_send.findViewById(R.id.file_name_text);
           file_send.setTag(viewHolder);
       }else {
           viewHolder = (ViewHolder) file_send.getTag();
       }
       try {
           if(list.get(position).getText()!=null){
               viewHolder.FileName.setText(list.get(position).getText());
           }else {
               viewHolder.FileName.setText("题目获取失败");
           }

           if(list.get(position).getDate()!=null){
               viewHolder.date.setText(list.get(position).getDate());
           }else {
               viewHolder.date.setText("题目获取失败");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return file_send;
    }

    public final class ViewHolder{
        public TextView date;
        public TextView FileName;


    }
}
