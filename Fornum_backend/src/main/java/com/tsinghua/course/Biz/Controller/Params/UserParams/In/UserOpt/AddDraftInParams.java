package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_ADDDRAFT)
public class AddDraftInParams extends CommonInParams {
    private String title;
    private String content;
    private String time;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTime() { return time; }
}
