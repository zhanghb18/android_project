package com.example.forum.ui.PersonalPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.user.UserApplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonHomeAdapter mAdapter;
    private TextView mNickName;
    private TextView mAboutMe;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_home);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.person_home_recycler_view);
        mAdapter = new PersonHomeAdapter(PersonHomeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PersonHomeActivity.this));

        mNickName = findViewById(R.id.person_home_my_nickname);
        mAboutMe = findViewById(R.id.person_aboutme);
        mNickName.setText(UserApplication.getNickname());
        //mAboutMe.setText(UserApplication.getAboutMe());

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.content = "内容" + i;
            momentList.add(moment);
            System.out.println(moment.content);
        }
    }
}