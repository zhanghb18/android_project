package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.MOMENT_GETPERSONAL)
public class PersonalMomentsInParams extends CommonInParams {
    @Required
    private String user_email;

    public String getUser_email() { return user_email; }
}
