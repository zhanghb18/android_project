package com.example.forum.ui.NoticePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.forum.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticeAdapter mAdapter;
    List<SingleNoticeMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.notice_recycler_view);
        mAdapter = new NoticeAdapter(NoticeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeActivity.this));
    }
}