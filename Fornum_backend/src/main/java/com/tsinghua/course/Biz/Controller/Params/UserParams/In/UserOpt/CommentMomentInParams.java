package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.MOMENT_COMMENT)
public class CommentMomentInParams extends CommonInParams {
    @Required
    // 动态发布用户
    private String post_email;
    @Required
    // 动态发布时间
    private String post_time;

    // 评论信息
    @Required
    private String comment;

    // 要回复的评论的发布用户
    private String reply_email;

    @Required
    private String comment_time;

    public String getPost_email() {
        return post_email;
    }

    public String getComment() {
        return comment;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getComment_time() {
        return comment_time;
    }

    public String getReply_email() { return reply_email; }
}
