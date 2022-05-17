package com.example.forum.ui.Person;

import androidx.appcompat.app.AppCompatActivity;
import com.example.forum.R;
import android.os.Bundle;

public class PersonInfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_person_info_page);
    }
}