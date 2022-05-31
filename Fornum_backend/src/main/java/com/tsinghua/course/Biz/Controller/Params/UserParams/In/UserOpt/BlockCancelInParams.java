package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_CANCELBL0CK)
public class BlockCancelInParams extends CommonInParams {
    @Required
    // 要取消关注的用户
    private String cancel_email;

    public String getCancel_email() {
        return cancel_email;
    }
}
