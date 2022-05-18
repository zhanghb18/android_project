package com.example.forum.ui.Person;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.forum.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.forum.user.UserApplication;
import com.example.forum.network.User;
import android.widget.Toast;

public class PersonInfoPage extends AppCompatActivity {

    private TextView mIdContentView;
    private TextView mNicknameContentView;
    private TextView mEmailContentView;
    private TextView mPersonInfoView;
    private Button mChangePwdButton;
    private Button mContentChangeButton;
    public static final String EXTRA_MESSAGE = "content";
    public ActivityResultLauncher<Intent> intentActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_person_info_page);
        mIdContentView = findViewById(R.id.ID_content);
        mNicknameContentView = findViewById(R.id.nickname_content);
        mEmailContentView = findViewById(R.id.email_content);
        mPersonInfoView = findViewById(R.id.person_info);
        mChangePwdButton = findViewById(R.id.change_pwd);
        mContentChangeButton = findViewById(R.id.save_button);
        mIdContentView.setText(UserApplication.getUserID());
        mNicknameContentView.setText(UserApplication.getNickname());
        mEmailContentView.setText(UserApplication.getEmail());
        
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //此处是跳转的result回调方法
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    String message = result.getData().getStringExtra(PersonInfoChangePage.REPLY_MESSAGE);
                    String []result_message = message.split(",");
                    if(result_message[0].equals("ID")){
                        mIdContentView.setText(result_message[1]);
                    }
                    if(result_message[0].equals("Nickname")){
                        mNicknameContentView.setText(result_message[1]);
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "回传失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        mChangePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonInfoPage.this, PwdConfirmActivity.class);
                startActivity(intent);
            }
        });
        mContentChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.ModifyInfo(view,UserApplication.getEmail(),mIdContentView.getText().toString(),mNicknameContentView.getText().toString());
                User.UserInfo(UserApplication.getEmail());
            }
        });
    }
    public void ID_change(View view) {
        Intent intent = new Intent(this, PersonInfoChangePage.class);
        String message = "ID," + mIdContentView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        System.out.println(message);
        intentActivityResultLauncher.launch(intent);
    }
    public void Nickname_change(View view) {
        Intent intent = new Intent(this, PersonInfoChangePage.class);
        String message = "Nickname," + mNicknameContentView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intentActivityResultLauncher.launch(intent);
    }
}