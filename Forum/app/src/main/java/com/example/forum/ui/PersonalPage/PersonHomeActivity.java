package com.example.forum.ui.PersonalPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.bean.GsonFunction;
import com.example.forum.network.RetrofitUtil;
import com.example.forum.network.UserAPI;
import com.example.forum.ui.PersonalPage.PersonHomeAdapter;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.user.UserApplication;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.view.View;
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

public class PersonHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonHomeAdapter mAdapter;
    private TextView mNickName;
    private TextView mAboutMe;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_home);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.person_home_recycler_view);
        mAdapter = new PersonHomeAdapter(PersonHomeActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PersonHomeActivity.this));

        mNickName = findViewById(R.id.person_home_my_nickname);
        mAboutMe = findViewById(R.id.person_aboutme);
        mNickName.setText(UserApplication.getNickname());
        //mAboutMe.setText(UserApplication.getAboutMe());

        // 获取个人动态
        System.out.println("获取个人动态");
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.GetPersonalMoments(UserApplication.getEmail());
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

//        for (int i = 1; i <= 10; i++) {
//            SingleMoment moment = new SingleMoment();
//            moment.content = "内容" + i;
//            momentList.add(moment);
//            System.out.println(moment.content);
//        }
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