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
import java.util.LinkedList;

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
                        String tmp = userInfo.getString("userID");
                        String userID = tmp.split("@")[0];
                        String aboutMe = tmp.split("@")[1];
                        System.out.println("userID");
                        System.out.println(userID);
                        System.out.println("aboutMe");
                        System.out.println(aboutMe);
                        UserApplication.setUserID(userID);
                        UserApplication.setNickname(userInfo.getString("nickname"));
                        UserApplication.setEmail(userInfo.getString("email"));
                        UserApplication.setAboutMe(aboutMe);
//                        String avatar_url = userInfoRes.getString("avatar");
//                        Call<ResponseBody> avatar_call = service.Avatar(avatar_url);
//                        avatar_call.enqueue(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                try {
//                                    byte[] bts = response.body().bytes();
//                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bts,0, bts.length);
//                                    UserApplication.setAvatar(bitmap);
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call call, Throwable t) {
//
//                            }
//                        });
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
        Call<ResponseBody> call = service.UserModifyInfo(email, userID, nickname, aboutMe);
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
                        UserApplication.setAboutMe(aboutMe);
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

    // 用户添加关注
    public static void AddStar(View view, String email, String star_email) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserAddStar(email, star_email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "添加关注失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户取消关注
    public static void CancelStar(View view, String email, String cancel_email) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserCancelStar(email, cancel_email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "取消关注失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户屏蔽
    public static void Block(View view, String email, String block_email) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserBlock(email, block_email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "添加黑名单失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户取消屏蔽
    public static void CancelBlock(View view, String email, String cancel_email) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.UserCancelBlock(email, cancel_email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        System.out.println("success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(view, "取消屏蔽失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户添加草稿箱
    public static void AddDraft(View v, String email, String title, String content) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.AddDraft(email, title, content, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        Snackbar.make(v, "保存草稿成功", Snackbar.LENGTH_SHORT).show();
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

    // 用户修改草稿
    public static void ModifyDraft(View v, String email, String title, String content, String old_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String new_time = simpleDateFormat.format(new Date());
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.ModifyDraft(email, title, content, old_time, new_time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        Snackbar.make(v, "修改草稿成功", Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(v, "修改失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户发布草稿
    public static void PostDraft(View v, String email, String title, String content, String old_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String post_time = simpleDateFormat.format(new Date());
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.PostDraft(email, title, content, old_time, post_time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        Snackbar.make(v, "发布草稿成功", Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(v, "发布失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户删除草稿
    public static void DeleteDraft(View v, String email, String time) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.DeleteDraft(email, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        Snackbar.make(v, "删除草稿成功", Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(v, "删除失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
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
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.LikeMoment(email, post_email, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        return;
                    }
                    else {
                        Snackbar.make(v, "Like Failed!", Snackbar.LENGTH_LONG)
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
                Snackbar.make(v, "添加失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户取消点赞
    public static void CancelLikeMoment(View v, String email, String post_email, String time) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.CancelLikeMoment(email, post_email, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        return;
                    }
                    else {
                        Snackbar.make(v, "Cancel Like Failed!", Snackbar.LENGTH_LONG)
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
                Snackbar.make(v, "取消失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    // 用户评论
    public static void CommentMoment(View v, String email, String post_email, String post_time, String comment, String reply_email, String comment_time) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<ResponseBody> call = service.CommentMoment(email, post_email, post_time, comment, reply_email, comment_time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject userInfoRes = GsonFunction.parseToJsonObject(response.body().string());
                    if (userInfoRes.getBoolean("success")) {
                        return;
                    }
                    else {
                        Snackbar.make(v, "Comment Failed!", Snackbar.LENGTH_LONG)
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
                Snackbar.make(v, "评论失败", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

}
