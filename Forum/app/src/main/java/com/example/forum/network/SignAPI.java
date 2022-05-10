package com.example.forum.network;

import java.util.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SignAPI {
    // 用邮箱和密码登录
    @GET("user/login")
    Call<ResponseBody> SignIn(@Query("userID") String email, @Query("password") String password);

    // 注册
    @GET("user/signup")
    Call<ResponseBody> SignUp( @Query("email") String email,@Query("userID") String userID,@Query("userID") String nickname,
                              @Query("password") String password);

    @GET("user/info")
    Call<ResponseBody> UserInfo();
}
