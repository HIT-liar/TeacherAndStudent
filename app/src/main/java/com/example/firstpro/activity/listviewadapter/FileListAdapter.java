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

import java.util.List;

public class FileListAdapter extends BaseAdapter {

    private List<FileToSend> list;
    private Context context;
    private LayoutInflater mInflater;

    public FileListAdapter(Context context, List<FileToSend>list){

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
           viewHolder.img = (ImageView) file_send.findViewById(R.id.file_img);
           viewHolder.FileName = (TextView) file_send.findViewById(R.id.file_name_text);
           file_send.setTag(viewHolder);
       }else {
           viewHolder = (ViewHolder) file_send.getTag();
       }
       try {
           if(list.get(position).GetFileName().contains(".docx")||list.get(position).GetFileName().contains(".doc")){
               viewHolder.img.setImageResource(R.drawable.word);
           }
           if (list.get(position).GetFileName().contains(".pptx")){
               viewHolder.img.setImageResource(R.drawable.ppt);
           }
           if (list.get(position).GetFileName().contains(".pdf")){
               viewHolder.img.setImageResource(R.drawable.pdf);
           }
           if(list.get(position).GetFileName()!=null){
               viewHolder.FileName.setText(list.get(position).GetFileName());
           }else {
               viewHolder.FileName.setText("文件名不详");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return file_send;
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView FileName;


    }
}
