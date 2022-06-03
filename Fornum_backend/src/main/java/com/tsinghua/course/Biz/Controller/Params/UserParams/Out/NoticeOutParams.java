package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

@BizType(BizTypeEnum.USER_NOTICE)
public class NoticeOutParams extends CommonOutParams {
    private String notice;

    public NoticeOutParams(String n) {
        this.notice = n;
        this.success = true;
    }

    public String getNotice() {
        return notice;
    }
}
