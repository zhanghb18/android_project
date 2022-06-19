package com.example.forum.ui.DraftPage;

import androidx.appcompat.app.AppCompatActivity;
import com.example.forum.R;
import com.example.forum.network.User;
import com.example.forum.network.UserAPI;
import com.example.forum.user.UserApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeMomentsActivity extends AppCompatActivity {

    private TextView mTitleView;
    private TextView mContentView;
    private Button save_button;
    private Button change_button;
    private Button delete_button;
    private String post_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_moments);
        getSupportActionBar().hide();

        mTitleView = findViewById(R.id.draft_change_title);
        mContentView = findViewById(R.id.draft_change_content);

        Intent intent = getIntent();
        Bundle message = intent.getExtras();
        post_time = message.getString("post_time");
        mTitleView.setText(message.getString("title"));
        mContentView.setText(message.getString("content"));

        save_button = findViewById(R.id.save_moments_button);
        change_button = findViewById(R.id.change_moments_button);
        delete_button = findViewById(R.id.delete_moments_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mContentView.getText().toString();
                String title = mTitleView.getText().toString();
                User.ModifyDraft(view,UserApplication.getEmail(),title,content,post_time);
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.DeleteDraft(view,UserApplication.getEmail(),post_time);
                finish();
            }
        });

        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mContentView.getText().toString();
                String title = mTitleView.getText().toString();
                User.PostDraft(view, UserApplication.getEmail(), title, content, post_time);
                finish();
            }
        });
    }
}