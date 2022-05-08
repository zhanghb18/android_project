package com.example.forum.user;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.forum.network.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserApplication extends Application {
    private static String username; // username = email
    private static String nickname;
    private static String signinfo;
    private static String password;
    private static String avatar_url;
    private static Bitmap avatar;

    public static String getSigninfo() {
        return signinfo;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getAvatar_url() {
        return avatar_url;
    }

    public static Bitmap getAvatar() {
        return avatar;
    }

    public static void setUsername(String username) {
        UserApplication.username = username;
    }

    public static void setSigninfo(String signinfo) {
        UserApplication.signinfo = signinfo;
    }

    public static void setPassword(String password) {
        UserApplication.password = password;
    }

    public static void setNickname(String nickname) {
        UserApplication.nickname = nickname;
    }

    public static void setAvatar_url(String url) {
        UserApplication.avatar_url = url;
    }

    public static void setAvatar(Bitmap bitmap) {
        UserApplication.avatar = bitmap;
    }

    private static boolean judge(String macthString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(macthString);
        return matcher.find();
    }

    public static boolean isValidNickname(String nickname) {
        return nickname.length() > 0 && nickname.length() < 15;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return judge(phoneNumber, "^1\\d{10}$");
    }

    public static boolean isValidPassword(String password, String rPassword) {
        if (!password.equals(rPassword)) return false;
        return judge(password, "^.{0,15}$");
    }
}
