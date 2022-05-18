package com.example.forum.ui.Person;

import androidx.appcompat.app.AppCompatActivity;
import com.example.forum.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.forum.network.User;
import com.example.forum.user.UserApplication;
import com.google.android.material.snackbar.Snackbar;

public class PwdChangeActivity extends AppCompatActivity {

    private EditText mnewPwdText;
    private EditText mnewVerifyText;
    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);
        getSupportActionBar().hide();
        mnewPwdText = findViewById(R.id.editNewTextPassword);
        mnewVerifyText = findViewById(R.id.editNewTextVerifyPassword);
        mConfirmButton = findViewById(R.id.new_pwd_confirm);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) view.getContext()
                        .getSystemService( Context.INPUT_METHOD_SERVICE );
                if (im.isActive()){
                    im.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                if(!mnewPwdText.getText().toString().equals(mnewVerifyText.getText().toString())) {
                    Snackbar.make(view, "两次输入密码不一致", Snackbar.LENGTH_SHORT).show();
                }
                else if(!UserApplication.isValidPassword(mnewPwdText.getText().toString(),mnewVerifyText.getText().toString())){
                    Snackbar.make(view, "密码格式不正确", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(PwdChangeActivity.this, PersonInfoPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    PwdChangeActivity.this.finish();
                }
            }
        });
    }
}