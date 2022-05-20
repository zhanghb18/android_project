package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.MOMENT_DELETECOMMENT)
public class DeleteCommentInParams extends CommonInParams {
    @Required
    // 所属动态的发布用户
    private String post_email;
    @Required
    // 动态发布时间
    private String post_time;

    @Required
    private String comment;

    // 被回复者
    private String reply_email;

    @Required
    private String comment_time;

    public String getComment_time() {
        return comment_time;
    }

    public String getReply_email() {
        return reply_email;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getPost_email() {
        return post_email;
    }

    public String getComment() {
        return comment;
    }
}
