package com.example.firstpro.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.firstpro.R;
import com.example.firstpro.activity.listviewadapter.FileListAdapter;
import com.example.firstpro.data.FileToSend;

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
    private List<FileToSend> list = new ArrayList<>();
    private ListView listView;
    private FileListAdapter fileListAdapter;

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

        FileToSend fileToSend1 = new FileToSend();
        list.add(fileToSend1);

        fileListAdapter = new FileListAdapter(getActivity(),list);
        listView.setAdapter(fileListAdapter);


    }

    private void initView() {

        Button button =(Button) rootView.findViewById(R.id.add_file);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}