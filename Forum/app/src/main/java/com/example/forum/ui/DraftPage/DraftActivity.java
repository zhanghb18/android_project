package com.example.forum.ui.DraftPage;

import androidx.appcompat.app.AppCompatActivity;
import com.example.forum.R;

import android.os.Bundle;

public class DraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        getSupportActionBar().hide();
    }
}