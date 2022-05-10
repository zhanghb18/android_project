package com.example.forum.ui.Sign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.forum.ForumActivity;
import com.example.forum.R;
import com.example.forum.network.Sign;
import com.google.android.material.snackbar.Snackbar;
//import com.luozm.captcha.Captcha;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPage extends Fragment {

    LinkedList<EditText> editTexts;

    public SignUpPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpPage.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpPage newInstance(String param1, String param2) {
        SignUpPage fragment = new SignUpPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signuppage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTexts = new LinkedList<>();
        editTexts.add(view.findViewById(R.id.editTextEmail));
        editTexts.add(view.findViewById(R.id.editTextId));
        editTexts.add(view.findViewById(R.id.editTextNickname));
        editTexts.add(view.findViewById(R.id.editTextPassword));
        editTexts.add(view.findViewById(R.id.editTextVerifyPassword));
        //Button verifyButton = view.findViewById(R.id.verify_button);
//        view.findViewById(R.id.SignUp_return).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SignUpPage.this)
//                        .navigate(R.id.action_SignUpFragment_to_InitFragment);
//            }
//        });
        boolean[] isVerified = {false};
        view.findViewById(R.id.rightarrow_Icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) v.getContext()
                        .getSystemService( Context.INPUT_METHOD_SERVICE );
                if (im.isActive()){
                    im.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
//                String username = username_edit.getText().toString();
//                String pwd = pwd_edit.getText().toString();
//                if (username == null || username.length() <= 0 || pwd == null || pwd.length() <=0){
//                    Snackbar.make(view, "Please fill your information.", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }
                // 关闭之前的所有activity 进入Seino主程序
                Intent intent = new Intent(getContext(), ForumActivity.class);
                getContext().startActivity(intent);
                //Sign.Login(getContext(), view, username, pwd);
            }
        });
    }

    private boolean judge(String macthString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(macthString);
        return matcher.find();
    }

    private boolean judgeSingleInfoCompleted(EditText editText) {
        int id = editText.getId();
        if (id == R.id.editTextUsername) return judge(editText.getText().toString(),
                                        "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");
        else if (id == R.id.editTextId) return judge(editText.getText().toString(),
                                            "^[a-zA-Z0-9_]+$");
        else if (id == R.id.editTextPassword) return judge(editText.getText().toString(),
                                                "^.{0,15}$");
        else return true;
    }

    private boolean judgeInfoCompleted() {
        for (EditText editText: editTexts) {
            if (!judgeSingleInfoCompleted(editText)) {
                Snackbar.make(getView(), editText.getHint() + "格式错误", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        // 密码不相等
        if (!editTexts.get(2).getText().toString().equals(editTexts.get(3).getText().toString())) {
            Snackbar.make(getView(), "两次输入密码不一致", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    private void verify(Runnable successRun) {
//        EditText editText = getView().findViewById(R.id.editTextEmail);
//        Sign.Verify(getContext(), getView(), editText.getText().toString(), successRun);
//    }
//
//    private void signup() {
//        // TODO
//        Sign.SignUp(getContext(), getView(), editTexts.get(0).getText().toString(),
//                editTexts.get(2).getText().toString(), editTexts.get(1).getText().toString(),
//                editTexts.get(4).getText().toString());
//    }
}