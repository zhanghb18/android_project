package com.tsinghua.course.Base.Enum;

/**
 * @描述 用户类型枚举
 **/
public enum NoticeType {
    LIKE("自己作品的点赞"),
    COMMENT("自己作品的评论"),
    UPDATE("关注作者的更新")
    ;

    NoticeType(String name) {
        this.name = name;
    }

    String name;

    public String getName() {
        return name;
    }
}
