package com.example.forum.ui.PersonalPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class OtherHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PersonHomeAdapter mAdapter;
    List<SingleMoment> momentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_home);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.person_home_recycler_view);
        mAdapter = new PersonHomeAdapter(OtherHomeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OtherHomeActivity.this));

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.content = "内容" + i;
            momentList.add(moment);
            System.out.println(moment.content);
        }
    }
}