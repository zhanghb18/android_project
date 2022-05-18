package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.MOMENT_LIKE)
public class LikeMomentInParams extends CommonInParams {
    // 朋友圈的发帖人
    @Required
    public String post_email;

    @Required
    public String time;

    public String getTime() {
        return time;
    }

    public String getPost_email() {
        return post_email;
    }
}
