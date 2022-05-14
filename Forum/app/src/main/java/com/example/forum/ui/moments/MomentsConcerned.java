package com.example.forum.ui.moments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;

import java.util.ArrayList;
import java.util.List;

public class MomentsConcerned extends Fragment {
    private RecyclerView recyclerView;
    private MomentsAdapter mAdapter;
    List<SingleMoment> momentList=new ArrayList<>();

    public MomentsConcerned(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moments_all, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new MomentsAdapter(getContext(), momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.content = "内容" + i;
            momentList.add(moment);
            System.out.println("?????????????????????????");
            System.out.println(moment.content);
        }
        // Inflate the layout for this fragment.
        return view;
    }
}
