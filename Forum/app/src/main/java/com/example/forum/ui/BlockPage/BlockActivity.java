package com.example.forum.ui.BlockPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.forum.R;
import com.example.forum.bean.GsonFunction;
import com.example.forum.network.RetrofitUtil;
import com.example.forum.network.UserAPI;
import com.example.forum.ui.ConcernPage.ConcernActivity;
import com.example.forum.ui.ConcernPage.ConcernAdapter;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.user.UserApplication;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BlockActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConcernAdapter mAdapter;
    List<SingleMoment> momentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.block_recycler_view);
        mAdapter = new ConcernAdapter(BlockActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BlockActivity.this));

        // 获取全部黑名单列表
        System.out.println("获取黑名单列表");
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserBlocks(UserApplication.getEmail());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                        String tmp = userInfoRes.getString("stars");
                        String[] stars = tmp.split("Stars");
                        for(String star : stars) {
                            if(star.endsWith(",")) star = star.substring(0, star.length()-1);
                            if(!star.contains("{")) continue;
                            System.out.println(star);
                            JSONObject jsonObject = GsonFunction.parseToJsonObject(star);
                            System.out.println(jsonObject);
                            SingleMoment momentItem = new SingleMoment();
                            momentItem.nickname = jsonObject.getString("nickname");
                            momentItem.email = jsonObject.getString("email");
                            momentItem.aboutMe = jsonObject.getString("aboutMe");
                            momentList.add(momentItem);
                        }
                        mAdapter.notifyDataSetChanged();
                        System.out.println(momentList);
                    }
                    else {
                        Snackbar.make(recyclerView, "Update Failed!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(recyclerView, "添加失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }
}