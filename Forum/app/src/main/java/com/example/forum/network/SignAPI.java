package com.example.forum.network;

import java.util.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SignAPI {
    @GET("user/login")
    Call<ResponseBody> SignIn(@Query("userID") String userID, @Query("password") String password);

//    @GET("user/verify")
//    Call<ResponseBody> Verify(@Query("phoneNumber") String phoneNumber);

    @GET("user/signup")
    Call<ResponseBody> SignUp(@Query("userID") String userID, @Query("email") String email,
                              @Query("password") String password);

    @GET("user/info")
    Call<ResponseBody> UserInfo();
}
