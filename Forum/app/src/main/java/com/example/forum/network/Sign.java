package com.example.forum.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

//import com.example.forum.ui.SeinoActivity;
import com.example.forum.ForumActivity;
import com.example.forum.user.UserApplication;
import com.example.forum.bean.GsonFunction;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Sign {
    private Sign() {

    }
    public static void Login(Context context, View view, String email, String password) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        SignAPI service = retrofit.create(SignAPI.class);
//        Log.d("Login", String.valueOf(android.os.Process.myTid()));
//        System.out.println(email);
//        System.out.println(password);
        Call<ResponseBody> call = service.SignIn(email, password);
//        System.out.println(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject signInRes = GsonFunction.parseToJsonObject(response.body().string());
                    System.out.println(signInRes);
                    if (signInRes.getBoolean("success")) {
                        System.out.println("success");
                        Log.d("Login Response", String.valueOf(android.os.Process.myTid()));
                        Thread.sleep(1000);
                        User.UserInfo(email);
//                        UserApplication.setUsername(username);
                        UserApplication.setPassword(password);
                        Intent intent = new Intent(context, ForumActivity.class);
                        context.startActivity(intent);
                    } else {
                        Snackbar.make(view, "??????????????????????????????", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
                } catch (IOException | JSONException | InterruptedException e) {
                    Snackbar.make(view, "?????????????????????????????????????????????", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "?????????????????????????????????????????????", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    public static void SignUp(Context context, View view, String email, String userID, String nickname,
                              String password) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        SignAPI service = retrofit.create(SignAPI.class);
        Call<ResponseBody> call = service.SignUp(email, userID, nickname, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject signUpRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (signUpRes.getBoolean("success")) {
                        System.out.println("????????????");
                        Login(context, view, email, password);
                    } else {
                        Snackbar.make(view, "??????????????????????????????", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t);
                Snackbar.make(view, "?????????????????????????????????????????????", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    public static void UserInfo(Context context, View view) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        SignAPI service = retrofit.create(SignAPI.class);
        Call call = service.UserInfo();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
