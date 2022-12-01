package com.example.firstpro.activity.stuclasshelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.stuactivity.MyClassesActivity;
import com.example.firstpro.activity.stuactivity.StuMyclassMessageActivity;
import com.example.firstpro.activity.teactivity.AddClassesActivity;
import com.example.firstpro.activity.teactivity.myclassesActivity;
import com.example.firstpro.data.Class;
import com.example.firstpro.database.AutoLoginStatic;
import com.example.firstpro.database.ChooseClassSQLIteHelper;
import com.example.firstpro.database.ClassesSQLIteHelper;
import com.example.firstpro.database.MySQLIteHelper;
import com.example.firstpro.activity.activityhelper.MyActivity;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {
    private Context mContext;
    private Button.OnClickListener mListener;
    private boolean isSelect;
    private List<Class> classList;
    Bundle bundle = new Bundle();

    ServerService sv = new ServerService();
    MyHandler myHandler = new MyHandler();
    private String stu_id = AutoLoginStatic.getInstance().getUserNum(mContext);
    private String class_id = "";

    private int max;
    private int true_num;

    public ClassInfoAdapter(Context context, Button.OnClickListener listener, boolean b, List<Class> classes) {
        this.mContext = context;
        this.mListener = listener;
        this.classList = classes;
    }

    public ClassInfoAdapter(Context context, boolean b, List<Class> classes) {
        this.mContext = context;
        isSelect = b;
        this.classList = classes;
    }


    @NonNull
    @Override
    public ClassInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_class_info, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ClassInfoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.aClass = classList.get(position);
        holder.position = position;
        if (!isSelect) {
            holder.classSelect_button.setText("退选");
        }
        holder.classID_text.setText(classList.get(position).getClass_id());
        bundle.putString("class_id",classList.get(position).getClass_id());
        class_id =classList.get(position).getClass_id();

        holder.className_text.setText(classList.get(position).getClass_name());
        bundle.putString("classname",classList.get(position).getClass_name());
        //这里老师名字的设定应该从远端数据库拉取，由account去users表中查询获得
        //String tea_account = classList.get(position).getTeacheraccount();
        holder.classTeacher_text.setText(classList.get(position).getTeacher_name());
        bundle.putString("teachername",classList.get(position).getTeacher_name());

        holder.classSchedule_text.setText(classList.get(position).getTime());
        bundle.putString("time",classList.get(position).getTime());
        holder.classRoom_text.setText(classList.get(position).getLocation_Of_Class());
        bundle.putString("location",classList.get(position).getLocation_Of_Class());
        holder.classCapacity_text.setText("容量：" + classList.get(position).getTrue_stu_num() + "/" + classList.get(position).getMax_stu_num());
        true_num =classList.get(position).getTrue_stu_num();
        bundle.putString("true_num",""+true_num);
        max =classList.get(position).getMax_stu_num();
        bundle.putString("max_num",""+max);
        bundle.putString("credit",""+classList.get(position).getCourse_credit());
        bundle.putString("class_num",""+classList.get(position).getClass_num());
//        holder.classIntro_button.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void OnClick(View view) {
//                AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
//                builder.setIcon(R.drawable.loginicon)
//                        .setTitle("课程详情")
//                        .setMessage(classList.get(position).getClassIntro())
//                        .create()
//                        .show();
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.OnClick(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ClassInfoViewHolder extends RecyclerView.ViewHolder {
        private Class aClass;
        private int position;
        private TextView classID_text;
        private TextView className_text;
        private TextView classTeacher_text;
        private TextView classSchedule_text;
        private TextView classRoom_text;
        private TextView classCapacity_text;
        private Button classIntro_button;
        private Button classSelect_button;

        public ClassInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            classID_text = itemView.findViewById(R.id.classID_text);
            className_text = itemView.findViewById(R.id.className_text);
            classTeacher_text = itemView.findViewById(R.id.teacher_text);
            classSchedule_text = itemView.findViewById(R.id.time_text);
            classRoom_text = itemView.findViewById(R.id.location_text);
            classCapacity_text = itemView.findViewById(R.id.num_text);
            classIntro_button = itemView.findViewById(R.id.ShowIntroButton);
            classIntro_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, StuMyclassMessageActivity.class);
                    intent.putExtra("bun",bundle);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(intent);


//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setIcon(R.drawable.loginicon)
//                            .setTitle("课程详情")
//                            .setMessage(aClass.getClassIntro())
//                            .create()
//                            .show();
                }
            });
            classSelect_button = itemView.findViewById(R.id.selectClassButton);
            classSelect_button.setOnClickListener(new View.OnClickListener() {
 //               StuDatabaseHelper sdb = new StuDatabaseHelper(mContext);
//                ChooseClassSQLIteHelper chooseClassSQLIteHelper = ChooseClassSQLIteHelper.getInstance(mContext);
//                String class_id = aClass.getClass_id();
//                String stu_id = AutoLoginStatic.getInstance().getUserNum(mContext);
                @Override
                public void onClick(View view) {
                    //mListener.onClick(view);
                    if (isSelect) {
                        if(max>true_num) {
                            ChoseClass();
                        }else {
                            Toast.makeText(mContext, "容量已满", Toast.LENGTH_LONG).show();
                        }
//                        String s = chooseClassSQLIteHelper.insertChoice(class_id,stu_id);
//                        Toast.makeText(mContext, "Select: " + s, Toast.LENGTH_LONG).show();
                    } else {
                        DeleteChoice();
//                        String s = chooseClassSQLIteHelper.deleteOne(class_id,stu_id);
//                        Toast.makeText(mContext, "Unselect: " + s, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnClick(int pos);
    }

    public void ChoseClass(){

        new Thread(new Runnable() {
            @Override
            public void run() {
              boolean flag =  sv.DeleteMyClass(class_id, MyURL.StuURL,false,stu_id,true);

                System.out.println(flag);
                Message msg = myHandler.obtainMessage();
                msg.what = 1;
                if(flag){
                    msg.arg1 =1;
                }else {
                    msg.arg1 =2;
                }
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    public void DeleteChoice(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean flag =sv.DeleteMyClass(class_id,MyURL.StuURL,false,stu_id,false);
                Message msg = myHandler.obtainMessage();
                msg.what = 2;
                if(flag){
                    msg.arg1 =1;
                }else {
                    msg.arg1 =2;
                }

                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChooseClassSQLIteHelper chooseClassSQLIteHelper = ChooseClassSQLIteHelper.getInstance(mContext);
            switch (msg.what) {
                case 1:
                    String s = chooseClassSQLIteHelper.insertChoice(class_id,stu_id);
                    if(msg.arg1==1)
                    Toast.makeText(mContext, "Select: " + s, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(mContext, "Select: false" , Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    String r = chooseClassSQLIteHelper.deleteOne(class_id,stu_id);
                    if(msg.arg1==1)
                        Toast.makeText(mContext, "UnSelect: " + r, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(mContext, "UnSelect: false" , Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}