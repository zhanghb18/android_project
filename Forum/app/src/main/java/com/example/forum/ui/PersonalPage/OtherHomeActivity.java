package com.example.forum.ui.PersonalPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.bean.GsonFunction;
import com.example.forum.network.RetrofitUtil;
import com.example.forum.network.UserAPI;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.network.User;
import com.example.forum.user.UserApplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtherHomeActivity extends AppCompatActivity {

    private String email;
    private String nickname;
    private String aboutMe;
    private RecyclerView recyclerView;
    private PersonHomeAdapter mAdapter;
    private Button mConcernButton;
    private Button mBlockButton;
    private TextView otherNicknameView;
    private TextView otherAboutMeView;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_home);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.other_home_recycler_view);
        mConcernButton = findViewById(R.id.concern_button);
        mBlockButton = findViewById(R.id.block_button);
        mAdapter = new PersonHomeAdapter(OtherHomeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OtherHomeActivity.this));

        for (int i = 1; i <= 10; i++) {
            SingleMoment moment = new SingleMoment();
            moment.content = "内容" + i;
            momentList.add(moment);
            System.out.println(moment.content);
        }

        otherNicknameView = findViewById(R.id.other_my_nickname);
        otherAboutMeView = findViewById(R.id.other_aboutme);
        Intent intent = getIntent();
        Bundle message = intent.getExtras();
        email = message.getString("email");
        nickname = message.getString("nickname");
        aboutMe = message.getString("aboutMe");
        otherNicknameView.setText(nickname);
        otherAboutMeView.setText(aboutMe);

        mConcernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConcernButton.getText().toString().equals("关注")) {
                    System.out.println("关注");
                    mConcernButton.setText("取消关注");
                    mConcernButton.setBackgroundColor(Color.parseColor("#1d9bf0"));
                    mConcernButton.setTextColor(Color.parseColor("#ffffff"));
                    mBlockButton.setText("屏蔽");
                    mBlockButton.setBackgroundColor(Color.parseColor("#ffffff"));
                    mBlockButton.setTextColor(Color.parseColor("#1d9bf0"));
                    User.AddStar(view, UserApplication.getEmail(),email);
                }
                else {
                    System.out.println("取消关注");
                    mConcernButton.setText("关注");
                    mConcernButton.setBackgroundColor(Color.parseColor("#ffffff"));
                    mConcernButton.setTextColor(Color.parseColor("#1d9bf0"));
                    User.CancelStar(view, UserApplication.getEmail(),email);
                }
            }
        });
        mBlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBlockButton.getText().toString().equals("屏蔽")) {
                    mBlockButton.setText("取消屏蔽");
                    mBlockButton.setBackgroundColor(Color.parseColor("#1d9bf0"));
                    mBlockButton.setTextColor(Color.parseColor("#ffffff"));
                    mConcernButton.setText("关注");
                    mConcernButton.setBackgroundColor(Color.parseColor("#ffffff"));
                    mConcernButton.setTextColor(Color.parseColor("#1d9bf0"));
                    User.Block(view,UserApplication.getEmail(),email);
                }
                else {
                    mBlockButton.setText("关注");
                    mBlockButton.setBackgroundColor(Color.parseColor("#ffffff"));
                    mBlockButton.setTextColor(Color.parseColor("#1d9bf0"));
                    User.CancelBlock(view,UserApplication.getEmail(),email);
                }
            }
        });

        // 获取个人动态
        System.out.println("获取个人动态");
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.GetPersonalMoments(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                        String tmp = userInfoRes.getString("moments");
                        String[] moments = tmp.split("Moment");
                        LinkedList<SingleMoment> items = new LinkedList<SingleMoment>();
                        for(String moment : moments) {
                            if(moment.endsWith(",")) moment = moment.substring(0, moment.length()-1);
                            if(!moment.contains("{")) continue;
                            SingleMoment momentItem = parser(moment);
                            momentList.add(momentItem);
                        }
                        mAdapter.notifyDataSetChanged();
                        System.out.println(momentList);
                    }
                    else {
                        System.out.println("not success");
//                        Snackbar.make(view, "Update Failed!", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failure");
//                Snackbar.make(view, "添加失败", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
            }
        });
    }

    private SingleMoment parser(String x) {
        SingleMoment momentItem = null;
        try {
            JSONObject jsonObject = GsonFunction.parseToJsonObject(x);
            boolean flag = false;
            String email = jsonObject.getString("email");
            String liked = "";
            String liked_list = "♥ : ";
            if(!jsonObject.getString("liked").equals(null)) {
                liked = jsonObject.getString("liked");
                liked = liked.substring(1, liked.length() - 1);
                String[] likeds = liked.split(",");
                if(liked.length() > 1) {
                    for(int i = 0; i < likeds.length; i++) {
                        String tmp = likeds[i].substring(1, likeds[i].length() - 1);
                        if (tmp.equals(email)){
                            flag = true;
                        }
                        liked_list += tmp + ",";
                    }
                }
            }
            momentItem = new SingleMoment(email, jsonObject.getString("title"), jsonObject.getString("content"),
                    jsonObject.getString("post_time"), jsonObject.getString("nickname"), jsonObject.getString("aboutMe"),
                    liked_list.substring(0, liked_list.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return momentItem;
    }
}