package com.example.forum.ui.DraftPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.NoticePage.NoticeActivity;
import com.example.forum.ui.NoticePage.NoticeAdapter;
import com.example.forum.ui.moments.SingleMoment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DraftActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DraftAdapter mAdapter;
    List<SingleMoment> momentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.draft_recycler_view);
        mAdapter = new DraftAdapter(DraftActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DraftActivity.this));
    }
}