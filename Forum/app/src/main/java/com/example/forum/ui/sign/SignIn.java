package com.example.forum.ui.sign;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.forum.R;
//import com.example.forum.network.Sign;
import com.google.android.material.snackbar.Snackbar;

public class SignIn extends Fragment {
    // 控件声明
    private EditText username_edit;
    private EditText pwd_edit;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signinpage, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.username_edit = view.findViewById(R.id.editTextUsername);
        this.pwd_edit = view.findViewById(R.id.editTextPassword);
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.SignIn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignIn.this)
                        .navigate(R.id.action_SignInFragment_to_InitFragment);
            }
        });
        // 切入Seino主程序
        view.findViewById(R.id.rightarrow_Icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) v.getContext()
                        .getSystemService( Context.INPUT_METHOD_SERVICE );
                if (im.isActive()){
                    im.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                String username = username_edit.getText().toString();
                String pwd = pwd_edit.getText().toString();
                if (username == null || username.length() <= 0 || pwd == null || pwd.length() <=0){
                    Snackbar.make(view, "Please fill your information.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                // 关闭之前的所有activity 进入Seino主程序
//                Intent intent = new Intent(getActivity().getApplicationContext(), SeinoActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                //Sign.Login(getContext(), view, username, pwd);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        View view = getView();
//        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", -1400);
//        anim.setDuration(400);
//        anim.start();
    }
}