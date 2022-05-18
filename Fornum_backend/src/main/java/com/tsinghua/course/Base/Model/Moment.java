package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Map;

public class Moment {
    public static class Comment {
        String username;
        String content;
        String time;
        // 回复者(评论朋友圈则为朋友圈的发表者)
        String reply_to_username;

        public Comment(String username, String reply_to_username, String content, String time) {
            this.username = username;
            this.reply_to_username = reply_to_username;
            this.content = content;
            this.time = time;
        }

        public String getUsername() {
            return username;
        }

        public String getTime() {
            return time;
        }

        public String getContent() {
            return content;
        }

        public String getReply_to_username() {
            return reply_to_username;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "username='" + username + '\'' +
                    ", content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    ", reply_to_username='" + reply_to_username + '\'' +
                    '}';
        }
    }

    // 发帖人的邮箱
    String email;

    // 发帖时间
    String post_time;

    // 图片的url
    String[] images;
    // 音频的url
    String[] audios;
    // 视频的url
    String[] videos;

    // 文本内容
    String content;

    // 点赞的用户（邮箱列表）
    String[] liked;

    // 评论记录
    Comment[] commentList;

    // 头像
    String avatar_url;

    public Moment(String email, String post_time) {
        this.email = email;
        this.post_time = post_time;
    }

    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }

    public Comment[] getCommentList() {
        return commentList;
    }

    public String getPost_time() {
        return post_time;
    }

    public String[] getImages() {
        return images;
    }

    public String[] getLiked() {
        return liked;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public boolean ifliked(String email) {
        if(liked != null) {
            for(int i = 0; i < liked.length; i++) {
                if (liked[i].equals(email)) return true;
            }
        }
        return false;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "email='" + email + '\'' +
                ", post_time='" + post_time + '\'' +
                ", images=" + Arrays.toString(images) +
                ", content='" + content + '\'' +
                ", liked=" + Arrays.toString(liked) +
                ", commentList=" + Arrays.toString(commentList) +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
