package com.example.forum.ui.moments;

import java.util.Vector;

import io.reactivex.Single;

public class SingleMoment {
    public String email;
    public String title;
    public String content;
    public String post_time;
    public String nickname;
    public String aboutMe;
    public String liked;

    public SingleMoment(String email, String title, String content, String post_time, String nickname, String aboutMe, String liked) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.post_time = post_time;
        this.nickname = nickname;
        this.aboutMe = aboutMe;
        this.liked = liked;
    }

    public SingleMoment() {

    }
}
