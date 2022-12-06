package com.example.firstpro.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firstpro.R;
import com.example.firstpro.WebService.MyURL;
import com.example.firstpro.WebService.ServerService;
import com.example.firstpro.activity.activityhelper.ActivityCollector;
import com.example.firstpro.activity.listviewadapter.FileListAdapter;
import com.example.firstpro.activity.teactivity.SendActivity;
import com.example.firstpro.activity.teactivity.StuIsSignActivity;
import com.example.firstpro.activity.teactivity.TextQuesActivity;
import com.example.firstpro.data.FileToSend;
import com.example.firstpro.data.Question;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;
    private List<Question> list = new ArrayList<>();
    private ListView listView;
    private FileListAdapter fileListAdapter;

    private ServerService sv = new ServerService();
    private MyHandler myHandler = new MyHandler((SendActivity) getActivity());


    public AddfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddfileFragment newInstance(String param1, String param2) {
        AddfileFragment fragment = new AddfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       if(rootView==null) {
           rootView = inflater.inflate(R.layout.fragment_addfile, container, false);
       }
       ListReact();
       initView();
           return  rootView;
    }

    private void ListReact() {
        listView = (ListView) rootView.findViewById(R.id.list_file_frag);

        //获得该课所有题目
        getQuesFromRemote();

        //todo:删除下面内容
//        Question q = new Question("11qq","1+2","2020-2-2");
//        q.setQuestionId(1);
//        list.add(q);
//        fileListAdapter = new FileListAdapter(getActivity(),list);
//        listView.setAdapter(fileListAdapter);


    }
    private void initView() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle =new Bundle();
                bundle.putInt("quesID",list.get(position).GetQuestionId());
                bundle.putString("quesText",list.get(position).getText());
                intent.putExtra("bun",bundle);
                intent.setClass(getActivity().getApplicationContext(), StuIsSignActivity.class);

                startActivity(intent);
            }
        });
    }

    private void getQuesFromRemote(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = sv.getQuestion(mParam1, MyURL.QAURL);
                Message msg = myHandler.obtainMessage();
                msg.obj = questions;
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }).start();
    }

    private class MyHandler extends Handler {

        //弱引用持有HandlerActivity , GC 回收时会被回收掉
        private WeakReference<SendActivity> weakReference;

        public MyHandler(SendActivity activity) {
            this.weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Question> que = (List<Question>) msg.obj;
            switch (msg.what) {
                case 1:
                    list.clear();
                    list.addAll(que);
                    fileListAdapter = new FileListAdapter(getActivity(),list);
                    listView.setAdapter(fileListAdapter);
                    break;
            }
        }
    }


}