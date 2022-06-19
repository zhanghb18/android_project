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
    Call<ResponseBody> UserModifyInfo(@Query("email") String email, @Query("userID") String userID, @Query("nickname") String nickname, @Query("aboutMe") String aboutMe);

    @GET("user/password")
    Call<ResponseBody> Password(@Query("email") String email, @Query("password") String password);

    @GET("user/add_star")
    Call<ResponseBody> UserAddStar(@Query("email") String email, @Query("star_email") String star_email);

    @GET("user/cancel_star")
    Call<ResponseBody> UserCancelStar(@Query("email") String email, @Query("cancel_email") String cancel_email);

    @GET("user/is_star")
    Call<ResponseBody> UserIsStar(@Query("email") String email, @Query("user_email") String user_email);

    @GET("user/block")
    Call<ResponseBody> UserBlock(@Query("email") String email, @Query("block_email") String block_email);

    @GET("user/cancel_block")
    Call<ResponseBody> UserCancelBlock(@Query("email") String email, @Query("cancel_email") String cancel_email);

    @GET("user/is_block")
    Call<ResponseBody> UserIsBlock(@Query("email") String email, @Query("user_email") String user_email);

    @GET("user/stars")
    Call<ResponseBody> UserStars(@Query("email") String email);

    @GET("user/blocks")
    Call<ResponseBody> UserBlocks(@Query("email") String email);

    @GET("user/notice")
    Call<ResponseBody> UserNotice(@Query("email") String email);

    @GET("user/notice_read")
    Call<ResponseBody> UserNoticeRead(@Query("email") String email, @Query("time") String time);

    @GET("user/add_draft")
    Call<ResponseBody> AddDraft(@Query("email") String email, @Query("title") String title, @Query("content") String content, @Query("time")String time);

    @GET("user/drafts")
    Call<ResponseBody> GetDraft(@Query("email") String email);

    @GET("user/modify_draft")
    Call<ResponseBody> ModifyDraft(@Query("email") String email, @Query("title") String title, @Query("content") String content,
                                   @Query("old_time") String old_time, @Query("new_time") String new_time);

    @GET("user/post_draft")
    Call<ResponseBody> PostDraft(@Query("email") String email, @Query("title") String title, @Query("content") String content,
                                   @Query("old_time") String old_time, @Query("post_time") String post_time);

    @GET("user/delete_draft")
    Call<ResponseBody> DeleteDraft(@Query("email") String email, @Query("time") String time);


    // Moment
    @GET("moment/post")
    Call<ResponseBody> PostNewMoment(@Query("email") String email, @Query("title") String title, @Query("content") String content, @Query("post_time")String post_time);

    @GET("moment/get")
    Call<ResponseBody> GetMoments(@Query("email") String email);

    @GET("moment/get_by_likes")
    Call<ResponseBody> GetMomentsByLikes(@Query("email") String email);

    @GET("moment/get_stars")
    Call<ResponseBody> GetStarMoments(@Query("email") String email);

    @GET("moment/get_stars_by_likes")
    Call<ResponseBody> GetStarMomentsByLikes(@Query("email") String email);

    @GET("moment/get_personal")
    Call<ResponseBody> GetPersonalMoments(@Query("user_email") String email);

    @GET("moment/like")
    Call<ResponseBody> LikeMoment(@Query("email") String email, @Query("post_email")String post_email, @Query("time") String time);

    @GET("moment/cancel_like")
    Call<ResponseBody> CancelLikeMoment(@Query("email") String email, @Query("post_email")String post_email, @Query("time") String time);

    @GET("moment/comment")
    Call<ResponseBody> CommentMoment(@Query("email") String email, @Query("post_email")String post_email, @Query("post_time") String post_time
                                    , @Query("comment") String comment, @Query("reply_email") String reply_email, @Query("comment_time") String comment_time);

    @GET("moment/delete_comment")
    Call<ResponseBody> DeleteComment(@Query("email") String email, @Query("post_email")String post_email, @Query("post_time") String post_time
            , @Query("comment") String comment, @Query("reply_email") String reply_email, @Query("comment_time") String comment_time);
}
