package com.example.forum.ui.ConcernPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.forum.R;
import com.example.forum.ui.ConcernPage.ConcernAdapter;
import com.example.forum.ui.PersonalPage.PersonHomeActivity;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;

import java.util.ArrayList;
import java.util.List;

public class ConcernActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConcernAdapter mAdapter;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.concern_recycler_view);
        mAdapter = new ConcernAdapter(ConcernActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ConcernActivity.this));

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.nickname = "wyx" + i;
            moment.aboutMe = "这个人很懒，什么都没有留下";
            momentList.add(moment);
            System.out.println(moment.content);
        }
    }
}