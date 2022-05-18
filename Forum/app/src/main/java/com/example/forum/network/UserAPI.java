package com.example.forum.network;

import com.example.forum.network.responseModel.UserModel;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserAPI {
    @GET("user/info")
    Call<ResponseBody> UserInfo(@Query("email") String email);

    @GET("user/nickname")
    Call<ResponseBody> Nickname(@Query("nickname") String nickname);

    @GET("user/phonenumber")
    Call<ResponseBody> PhoneNumber(@Query("phoneNumber") String phoneNumber);

    @GET("user/password")
    Call<ResponseBody> Password(@Query("password") String password, @Query("new_password") String newPassword);

    @GET
    Call<ResponseBody> Avatar(@Url String url);

    @GET("user/addcontact")
    Call<ResponseBody> AddContact(@Query("friendUsername") String friend_username);

    @GET("user/openchat")
    Call<ResponseBody> OpenChat(@Query("id") String id, @Query("type") String type);

    @GET("user/deletecontact")
    Call<ResponseBody> DeleteContact(@Query("friend_username") String friend_username);

    @GET("test/file")
    Call<ResponseBody> FileTest(@Query("file") File file);

    @GET("user/postdiscover")
    Call<ResponseBody> PostDiscover(@Query("content") String content, @Query("post_time")String post_time);

    @GET("user/getdiscover")
    Call<ResponseBody> GetDiscover();

    @GET("user/likediscover")
    Call<ResponseBody> LikeDiscover(@Query("post_username")String post_username, @Query("time") String time);

    @GET("user/cancellike")
    Call<ResponseBody> UnLikeDiscover(@Query("post_username")String post_username, @Query("time") String time);


}
