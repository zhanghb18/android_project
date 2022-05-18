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

    public static void UserInfo(String email) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Log.d("User Info", "start");
        Call<ResponseBody> call = service.UserInfo(email);
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

    public static void ModifyInfo(View view,String email, String userID, String nickname) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserModifyInfo(email, userID, nickname);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    System.out.println(userInfoRes);
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println(userInfoRes);
                        UserApplication.setUserID(userID);
                        UserApplication.setNickname(nickname);
                        Snackbar.make(view, "保存成功", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = userInfoRes.getString("msg");
                        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Snackbar.make(view, "修改失败", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
            }
        });
    }

    public static void Password(String email, String password) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.Password(email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        UserApplication.setPassword(password);
//                        Thread.sleep(200);
//                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Snackbar.make(view, "修改失败", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
            }
        });
    }
}
