package com.example.forum.ui.moments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.forum.user.UserApplication;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.ForumActivity;
import com.example.forum.R;
import com.example.forum.ui.Person.PersonInfoChangePage;
import com.example.forum.ui.Person.PersonInfoPage;
import com.example.forum.ui.Person.PwdConfirmActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MomentsAll extends Fragment {

    private RecyclerView recyclerView;
    private MomentsAdapter mAdapter;
    List<SingleMoment> momentList = new ArrayList<>();
    public ActivityResultLauncher<Intent> intentActivityResultLauncher;

    public MomentsAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moments_all, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new MomentsAdapter(getContext(), momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //此处是跳转的result回调方法
                System.out.println("result");
                System.out.println(result.getData());
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle message = result.getData().getExtras();
                    System.out.println(message);
                    //Bundle bundle = intent.getExtras();
                    String title = message.getString("title");
                    //Log.d(LOG_TAG, title);
                    String content = message.getString("content");
                    //Log.d(LOG_TAG, content);
                    System.out.println("bundle");
                    System.out.println(title);
                    System.out.println(content);
                    SingleMoment moment = new SingleMoment();
                    moment.title = title;
                    moment.content = content;
                    moment.email = UserApplication.getEmail();
                    momentList.add(0, moment);
                    System.out.println("---------");
                    System.out.println(momentList.get(0).email);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getApplicationContext(), "回传失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMomentsActivity.class);
                intentActivityResultLauncher.launch(intent);
            }
        });
//        for (int i = 1; i <= 10; i++) {
//            SingleMoment moment = new SingleMoment();
//            moment.title = "标题" + i;
//            moment.content = "内容" + i;
//            momentList.add(moment);
//        }
        // Inflate the layout for this fragment.
        return view;
    }
}