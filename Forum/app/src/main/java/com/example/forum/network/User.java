package com.example.forum.network;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.example.forum.user.UserApplication;
import com.example.forum.bean.GsonFunction;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class User {
    private User() {

    }

    public static void UserInfo() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Log.d("User Info", "start");
        Call<ResponseBody> call = service.UserInfo();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    Log.d("nickname", "success");
                    if(userInfoRes.getBoolean("success")) {
                        String str = userInfoRes.getString("userInfo").substring(4);
                        JSONObject userInfo = GsonFunction.parseToJsonObject(str);
                        UserApplication.setUserID(userInfo.getString("userID"));
                        UserApplication.setNickname(userInfo.getString("nickname"));
                        UserApplication.setEmail(userInfo.getString("email"));
                        UserApplication.setAvatar_url(userInfoRes.getString("avatar"));
                        String avatar_url = userInfoRes.getString("avatar");
                        Call<ResponseBody> avatar_call = service.Avatar(avatar_url);
                        avatar_call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    byte[] bts = response.body().bytes();
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bts,0, bts.length);
                                    UserApplication.setAvatar(bitmap);
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("User Info Failed", "");
            }
        });
    }
}