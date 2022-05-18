package com.example.forum.user;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.forum.network.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserApplication extends Application {
    private static String userID;
    private static String email;
    private static String nickname;
    private static String signinfo;
    private static String password;
    private static String aboutMe;
    private static String avatar_url;
    private static Bitmap avatar;

    public static String getSigninfo() {
        return signinfo;
    }

    public static String getUserID() {
        return userID;
    }

    public static String getEmail() { return email; }

    public static String getPassword() {
        return password;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getAboutMe() { return aboutMe; }

    public static String getAvatar_url() {
        return avatar_url;
    }

    public static Bitmap getAvatar() {
        return avatar;
    }

    public static void setUserID(String id) {
        UserApplication.userID = id;
    }

    public static void setEmail(String e) { email = e; }

    public static void setSigninfo(String signinfo) {
        UserApplication.signinfo = signinfo;
    }

    public static void setPassword(String password) {
        UserApplication.password = password;
    }

    public static void setNickname(String nickname) {
        UserApplication.nickname = nickname;
    }

    public static void setAboutMe(String a) { UserApplication.aboutMe = a; }

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
