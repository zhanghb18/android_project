package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;
import com.tsinghua.course.Base.Enum.NoticeType;

import java.util.Map;

public class Notice {
    // 接收人的用户名
    String my_username;

    // 关注人的用户名
    String star_username;

    // 通知时间
    String post_time;

    // 通知内容
    String content;

    // 通知类型
    NoticeType type;

    public String getMyUsername() {
        return my_username;
    }

    public void setMyUsername(String username) {
        this.my_username = username;
    }

    public String getStarUsername() { return star_username; }

    public void setStarUsername(String usename) { this.star_username = usename;}

    public String getPostTime() { return post_time; }

    public void setPostTime(String time) { this.post_time = time; }

    public String getContent() { return content; }

    public void setContent(String c) { this.content = c; }

    public NoticeType getNoticeType() { return type; }

    public void setNoticeType(NoticeType nt) { this.type = nt; }
}
