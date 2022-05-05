package com.example.forum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.forum.R;

public class InitPage extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initpage, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InitPage.this)
                        .navigate(R.id.action_InitPage_to_SignInFragment);
            }
        });
        view.findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // 测试连接后端
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
//                                .connectTimeout(10, TimeUnit.SECONDS)
//                                .readTimeout(10, TimeUnit.SECONDS)
//                                .build();
//                        // ip地址换成自己的电脑本地外部端口
//                        Request request = new Request.Builder()
//                                .get()
//                                .url("http://192.168.1.6:7000/test/redis")
//                                .build();
//                        try {
//                            Response req = mOkHttpClient.newCall(request).execute();
//                            Log.d("thread", req.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                NavHostFragment.findNavController(InitPage.this)
                        .navigate(R.id.action_InitPage_to_SignUpFragment);
            }
        });
    }
}