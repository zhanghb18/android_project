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
import com.example.forum.user.UserApplication;
import com.google.android.material.snackbar.Snackbar;

public class PwdConfirmActivity extends AppCompatActivity {

    private EditText mPwdText;
    private Button mPwdConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_confirm);
        getSupportActionBar().hide();
        mPwdConfirmButton = findViewById(R.id.pwd_confirm);
        mPwdText = findViewById(R.id.pwd_change_content);
        mPwdConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) view.getContext()
                        .getSystemService( Context.INPUT_METHOD_SERVICE );
                if (im.isActive()){
                    im.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                if(mPwdText.getText().toString().equals(UserApplication.getPassword())){
                    Intent intent = new Intent(PwdConfirmActivity.this, PwdChangeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    PwdConfirmActivity.this.finish();
                }
                else {
                    Snackbar.make(view, "密码错误请重新输入！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            }
        });
    }
}