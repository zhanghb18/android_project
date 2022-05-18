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
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
                        System.out.println("userInfo");
                        System.out.println(userInfoRes.getString("userInfo"));
                        String str = userInfoRes.getString("userInfo").substring(4);
                        System.out.println(str);
                        JSONObject userInfo = GsonFunction.parseToJsonObject(str);
                        System.out.println(userInfo);
                        UserApplication.setUserID(userInfo.getString("userID"));
                        UserApplication.setNickname(userInfo.getString("nickname"));
                        UserApplication.setEmail(userInfo.getString("email"));
                        UserApplication.setAvatar_url(userInfoRes.getString("avatar"));
                        UserApplication.setAboutMe(userInfoRes.getString("aboutMe"));
                        System.out.println(userInfoRes.getString("aboutMe"));
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

    public static void ModifyInfo(View view,String email, String userID, String nickname, String aboutMe) {
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

    // 用户发布动态
    public static void PostMoment(View v, String email, String title, String content) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.PostNewMoment(email, title, content, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        Snackbar.make(v, "发布成功", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = userInfoRes.getString("msg");
                        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(v, "添加失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户点赞动态
    public static void likeMoment(View v, String email, String post_email, String time) {

//        Retrofit retrofit = RetrofitUtil.getRetrofit();
//        UserAPI service = retrofit.create(UserAPI.class);
//        Call<ResponseBody> call = service.LikeMoment(email, post_email, time);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
//                    if (userInfoRes.getBoolean("success")) {
//                        return;
//                    }
//                    else {
//                        Snackbar.make(v, "Like Failed!", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Snackbar.make(v, "添加失败", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
//            }
//        });
//
//
//        discover.setStarred();
//        if(discover.getStarred()){
//            holder.starred.setImageResource(android.R.drawable.star_big_on);
//            Retrofit retrofit = RetrofitUtil.getRetrofit();
//            UserAPI service = retrofit.create(UserAPI.class);
//            Call<ResponseBody> call = service.LikeMoment(holder.nickname.getText().toString(),
//                    holder.discover_time.getText().toString());
//
//        }
//        else{
//            holder.starred.setImageResource(android.R.drawable.star_big_off);
//            Retrofit retrofit = RetrofitUtil.getRetrofit();
//            UserApi service = retrofit.create(UserApi.class);
//            Call<ResponseBody> call = service.UnLikeDiscover(holder.nickname.getText().toString(),
//                    holder.discover_time.getText().toString());
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
//                        if (userInfoRes.getBoolean("success")) {
//                            return;
//                        }
//                        else {
//                            Snackbar.make(v, "Like Failed!", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
//                            return;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Snackbar.make(v, "添加失败", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null)
//                            .show();
//                }
//            });
//        }
//        //TODO 更新点赞列表
//        StringBuilder starredPeople = new StringBuilder();
//        ArrayList<String> goodUser = discover.getGoodUser();
//        for(int i=0;i<goodUser.size();i++){
//            if(i==0){
//                starredPeople.append("♥: ");
//            }
//            starredPeople.append(goodUser.get(i));
//            if(i!=goodUser.size()-1){
//                starredPeople.append(",");
//            }
//        }
//        holder.starredPeople.setText(starredPeople.toString());
    }
}
