package com.example.forum.ui.moments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.forum.ForumActivity;
import com.example.forum.R;
import com.example.forum.network.User;
import com.example.forum.ui.Person.PersonInfoPage;
import com.example.forum.user.UserApplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddMomentsActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Button save_to_draft_button;
    public static final String REPLY_MESSAGE = "content_change";
    private String[] result;
    private String message;

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoments);
        getSupportActionBar().hide();
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        save_to_draft_button = findViewById(R.id.save_moments_to_draft_button);
        save_to_draft_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String inputTitle = title.getText().toString();
                String inputContent = content.getText().toString();
                User.AddDraft(view, UserApplication.getEmail(), inputTitle, inputContent);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    public void returnReply(View view) {
        Intent intent = new Intent();
        String inputTitle = title.getText().toString();
        String inputContent = content.getText().toString();
        User.PostMoment(view, UserApplication.getEmail(), inputTitle, inputContent);
        Bundle bundle = new Bundle();
        bundle.putString("title", inputTitle);
        bundle.putString("content", inputContent);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

}
