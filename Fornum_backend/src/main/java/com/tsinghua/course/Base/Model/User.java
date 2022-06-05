package com.tsinghua.course.Base.Model;

import com.tsinghua.course.Base.Enum.NoticeType;
import com.tsinghua.course.Base.Enum.UserType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * @描述 对应mongodb中的User集合，mongodb是非关系型数据库，可以存储的对象类型很丰富，使用起来方便很多
 **/
@Document("User")
public class User {
    /** 子对象文档 */
    public static class SubObj {
        /** 存储的时间 */
        private String time;
        private String name;
        private String contentType;
        private String url;

        public SubObj(String t, String n, String c, String u) {
            this.time = t;
            this.name = n;
            this.contentType = c;
            this.url = u;
        }

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }

        public String getName() { return name; }
        public void setName(String n) { this.name = n; }

        public String getCotentType() { return contentType; }
        public void setContentType(String c) { this.contentType = c; }

        public String getUrl() { return url; }
        public void setUrl(String u) { this.url = u; }
    }

    public User(String email, String userID, String nickname, String password, String aboutMe){
        this.email = email;
        this.userID = userID;
        this.nickname = nickname;
        this.password = password;
        this.userType = UserType.NORMAL;
        this.aboutMe = aboutMe;
        this.star = null;
        this.black = null;
        this.fan = null;
    }

    /* 关注的用户 */
    public static class Stars {
        // 用户
        private String email;

        // 添加时间
        private String time;

        // 用户头像url
        private String avatar_url;

        public String getAvatar_url() { return this.avatar_url; }

        public void setAvatar_url(String av) { this.avatar_url = av; }

        public Stars(@NonNull String email, String time, String avatar_url) {
            this.email = email;
            this.time = time;
            this.avatar_url = avatar_url;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String e) {
            this.email = e;
        }

        public String starString(String nickname, String aboutMe) {
            String e = email.replace("%40", "@");
            return "Stars{" +
                    "email='" + e + '\'' +
                    "nickname='" + nickname + '\'' +
                    "aboutMe='" + aboutMe + '\'' +
                    '}';
        }
    }

    /* 粉丝用户 */
    public static class Fans {
        // 用户
        private String email;

        public Fans(@NonNull String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String e) {
            this.email = e;
        }
    }

    /* 屏蔽的用户 */
    public static class Blacks {
        // 用户
        private String email;

        public Blacks(@NonNull String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String e) {
            this.email = e;
        }

        @Override
        public String toString() {
            return "Blacks{" +
                    "email='" + email + '\'' +
                    '}';
        }
    }

    // 通知
    public static class Notices {
        // 通知类型
        private NoticeType type;

        // 该通知的关联用户
        private String email;

        // 通知时间
        private String post_time;

        // 通知内容
        private String content;

        // 是否已读
        private boolean ifRead;

        public boolean getIfRead() { return ifRead; }

        public void setIfRead(boolean i) { this.ifRead = i; }

        public String getEmail() { return email; }

        public void setEmail(String e) { this.email = e;}

        public String getPostTime() { return post_time; }

        public void setPostTime(String time) { this.post_time = time; }

        public String getContent() { return content; }

        public void setContent(String c) { this.content = c; }

        public NoticeType getNoticeType() { return type; }

        public void setNoticeType(NoticeType nt) { this.type = nt; }

        public Notices(String email, NoticeType type, String time) {
            this.email = email;
            this.type = type;
            this.ifRead =false;
            this.post_time = time;
        }

        public String noticeString(String nickname) {
            switch (type) {
                case LIKE:
                    return "Notice{" +
                            "notice='" + "用户" + nickname + "给你点赞啦！" + '\'' +
                            "ifRead='" + ifRead +
                            "time='" + post_time +
                            '}';
                case COMMENT:
                    return "Notice{" +
                            "notice='" + "用户" + nickname + "评论了你！" + '\'' +
                            "ifRead='" + ifRead +
                            "time='" + post_time +
                            '}';
                case UPDATE:
                    return "Notice{" +
                            "notice='" + "你关注的用户" + nickname + "发布新动态啦！" + '\'' +
                            "ifRead='" + ifRead +
                            "time='" + post_time +
                            '}';
                default:
                    return "error";
            }

        }
    }

    // mongodb唯一id
    String id;
    // 邮箱
    String email;
    // 用户ID
    String userID;
    // 昵称
    String nickname;
    // 密码
    String password;
    // 用户类型
    UserType userType;
    // 个人简介
    String aboutMe;
    // 测试数组
    String[] testArr;
    // 测试对象
    Map<String, String> testObj;
    // 另一个测试对象，和 Map<String, String> 方式存储的格式是一样的，但是直观很多
    SubObj subObj;

    // 账号创建时间
    String time;

    // 关注的用户
    Stars[] star;
    // 粉丝用户
    Fans[] fan;
    // 屏蔽的用户
    Blacks[] black;

    // 用户通知
    Notices[] notices;

    public Fans[] getFan() { return fan; }

    public void setFan(Fans[] f) { this.fan = f; }

    public Notices[] getNotice() { return notices; }

    public void setNotice(Notices[] n) { this.notices = n; }

    public Stars[] getStar() { return star; }

    public void setStar(Stars[] s) { this.star = s; }

    public Blacks[] getBlack() { return black; }

    public void setBlack(Blacks[] b) { this.black = b; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() { return email; }

    public void setEmail(String e) { this.email = e; }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String username) {
        this.userID = username;
    }

    public String getNickname() { return nickname; }

    public void setNickname(String n) { this.nickname = n; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getAboutMe() { return aboutMe; }

    public void setAboutMe(String a) { this.aboutMe = a; }

    public String[] getTestArr() {
        return testArr;
    }

    public void setTestArr(String[] testArr) {
        this.testArr = testArr;
    }

    public Map<String, String> getTestObj() {
        return testObj;
    }

    public void setTestObj(Map<String, String> testObj) {
        this.testObj = testObj;
    }

    public SubObj getSubObj() {
        return subObj;
    }

    public void setSubObj(SubObj subObj) {
        this.subObj = subObj;
    }

    public String infoString() {
        String decoded_email = email.replace("%40", "@");
        return "User{" +
                "email='" + decoded_email + '\'' +
                ", userID='" + userID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
