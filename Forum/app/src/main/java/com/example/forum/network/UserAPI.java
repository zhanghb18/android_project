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
    // User
    @GET("user/info")
    Call<ResponseBody> UserInfo(@Query("email") String email);

    @GET("user/modify_info")
    Call<ResponseBody> UserModifyInfo(@Query("email") String email, @Query("userID") String userID, @Query("nickname") String nickname);

    @GET("user/password")
    Call<ResponseBody> Password(@Query("email") String email, @Query("password") String password);

    @GET("user/add_star")
    Call<ResponseBody> UserAddStar(@Query("email") String email, @Query("star_email") String star_email);

    @GET("user/cancel_star")
    Call<ResponseBody> UserCancelStar(@Query("email") String email, @Query("cancel_email") String cancel_email);

    @GET("user/block")
    Call<ResponseBody> UserBlock(@Query("email") String email, @Query("block_email") String block_email);

    @GET("user/cancel_block")
    Call<ResponseBody> UserCancelBlock(@Query("email") String email, @Query("cancel_email") String cancel_email);

    @GET("user/stars")
    Call<ResponseBody> UserStars(@Query("email") String email);

    // Moment
    @GET("moment/post")
    Call<ResponseBody> PostNewMoment(@Query("email") String email, @Query("title") String title, @Query("content") String content, @Query("post_time")String post_time);

    @GET("moment/like")
    Call<ResponseBody> LikeMoment(@Query("email") String email, @Query("post_email")String post_email, @Query("time") String time);

    @GET("moment/get")
    Call<ResponseBody> GetMoments(@Query("email") String email);

    @GET("moment/get_personal")
    Call<ResponseBody> GetPersonalMoments(@Query("email") String email);




    @GET
    Call<ResponseBody> Avatar(@Url String url);



    @GET("user/openchat")
    Call<ResponseBody> OpenChat(@Query("id") String id, @Query("type") String type);

    @GET("user/deletecontact")
    Call<ResponseBody> DeleteContact(@Query("friend_username") String friend_username);

    @GET("test/file")
    Call<ResponseBody> FileTest(@Query("file") File file);





    @GET("user/cancellike")
    Call<ResponseBody> UnLikeDiscover(@Query("post_username")String post_username, @Query("time") String time);


}
