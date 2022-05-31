package com.example.forum.ui.PersonalPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OtherHomeActivity extends AppCompatActivity {

    private String email;
    private String nickname;
    private String aboutMe;
    private RecyclerView recyclerView;
    private PersonHomeAdapter mAdapter;
    private Button mConcernButton;
    private TextView otherNicknameView;
    private TextView otherAboutMeView;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_home);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.person_home_recycler_view);
        mConcernButton = findViewById(R.id.concern_button);
        mAdapter = new PersonHomeAdapter(OtherHomeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OtherHomeActivity.this));

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.content = "内容" + i;
            momentList.add(moment);
            System.out.println(moment.content);
        }

        otherNicknameView = findViewById(R.id.other_my_nickname);
        otherAboutMeView = findViewById(R.id.other_aboutme);
        Intent intent = new Intent();
        Bundle message = intent.getExtras();
        email = message.getString("email");
        nickname = message.getString("nickname");
        aboutMe = message.getString("aboutMe");
        otherNicknameView.setText(nickname);

        mConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConcernButton.getText().toString().equals("关注")) {
                    mConcernButton.setText("取消关注");
                    mConcernButton.setBackgroundColor(Color.parseColor("#1d9bf0"));
                    mConcernButton.setTextColor(Color.parseColor("#ffffff"));
                }
                if(mConcernButton.getText().toString().equals("取消关注")) {
                    mConcernButton.setText("关注");
                    mConcernButton.setBackgroundColor(Color.parseColor("#ffffff"));
                    mConcernButton.setTextColor(Color.parseColor("#1d9bf0"));
                }
            }
        });
    }
}