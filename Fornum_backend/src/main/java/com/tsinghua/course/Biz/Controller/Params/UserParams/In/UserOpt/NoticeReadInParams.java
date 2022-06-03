package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_NOTICEREAD)
public class NoticeReadInParams extends CommonInParams {
    // 该通知的创建时间
    private String notice_time;

    public String getNotice_time() { return notice_time; }
}
