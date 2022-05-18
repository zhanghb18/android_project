package com.example.forum.ui.Person;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import com.example.forum.R;

public class PersonInfoChangePage extends AppCompatActivity {

    public static final String REPLY_MESSAGE = "content_change";
    private String message;
    private TextView mContent;
    private String[] result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_person_info_change_page);
        Intent intent = getIntent();
        message = intent.getStringExtra(PersonInfoPage.EXTRA_MESSAGE);
        System.out.println(message);
        result = message.split(",");
        mContent = findViewById(R.id.change_content);
        mContent.setText(result[1]);
    }

    public void save_change(View view) {
        Intent intent = new Intent();
        String message = result[0] + "," + mContent.getText().toString();
        intent.putExtra(REPLY_MESSAGE, message);
        setResult(RESULT_OK,intent);
        finish();
    }
}