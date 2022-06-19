package com.example.forum.ui.DraftPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.example.forum.bean.GsonFunction;
import com.example.forum.network.RetrofitUtil;
import com.example.forum.network.UserAPI;
import com.example.forum.ui.NoticePage.NoticeActivity;
import com.example.forum.ui.NoticePage.NoticeAdapter;
import com.example.forum.ui.NoticePage.SingleNoticeMoment;
import com.example.forum.ui.moments.SingleMoment;
import com.example.forum.user.UserApplication;

import android.os.Bundle;

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

public class DraftActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DraftAdapter mAdapter;
    List<SingleMoment> momentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.draft_recycler_view);
        mAdapter = new DraftAdapter(DraftActivity.this,momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DraftActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取全部动态列表
        momentList.clear();
        // 获取草稿箱列表
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.GetDraft(UserApplication.getEmail());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                        String tmp = userInfoRes.getString("drafts");
                        String[] moments = tmp.split("Draft");
                        for(String moment : moments) {
                            if(moment.endsWith(",")) moment = moment.substring(0, moment.length()-1);
                            if(!moment.contains("{")) continue;
                            JSONObject jsonObject = GsonFunction.parseToJsonObject(moment);
                            String title = jsonObject.getString("title");
                            String content = jsonObject.getString("content");
                            String time = jsonObject.getString("time");
                            SingleMoment momentItem = new SingleMoment();
                            momentItem.title = title;
                            momentItem.content = content;
                            momentItem.post_time = time;
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
}