package com.example.forum.ui.moments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forum.bean.GsonFunction;
import com.example.forum.network.RetrofitUtil;
import com.example.forum.network.UserAPI;
import com.example.forum.user.UserApplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forum.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class MomentsAll extends Fragment {

    private RecyclerView recyclerView;
    private MomentsAdapter mAdapter;
    List<SingleMoment> momentList = new ArrayList<>();
    public ActivityResultLauncher<Intent> intentActivityResultLauncher;

    public MomentsAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moments_all, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new MomentsAdapter(getContext(), momentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //此处是跳转的result回调方法
                System.out.println("result");
                System.out.println(result.getData());
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle message = result.getData().getExtras();
                    System.out.println(message);
                    String title = message.getString("title");
                    String content = message.getString("content");
                    System.out.println("bundle");
                    System.out.println(title);
                    System.out.println(content);
                    SingleMoment moment = new SingleMoment();
                    moment.title = title;
                    moment.content = content;
                    moment.email = UserApplication.getEmail();
                    momentList.add(0, moment);
                    System.out.println("---------");
                    System.out.println(momentList.get(0).email);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getApplicationContext(), "回传失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMomentsActivity.class);
                intentActivityResultLauncher.launch(intent);
            }
        });

        // 获取全部动态列表
        System.out.println("获取动态");
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.GetMoments();
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
                            items.addFirst(momentItem);
                        }
                        momentList = items;
                        System.out.println(momentList);
                    }
                    else {
                        Snackbar.make(view, "Update Failed!", Snackbar.LENGTH_LONG)
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
                Snackbar.make(view, "添加失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

//        for (int i = 1; i <= 10; i++) {
//            SingleMoment moment = new SingleMoment();
//            moment.title = "标题" + i;
//            moment.content = "内容" + i;
//            momentList.add(moment);
//        }
        // Inflate the layout for this fragment.
        return view;
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