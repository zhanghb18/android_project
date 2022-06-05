package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_MODIFYDRAFT)
public class ModifyDraftInParams extends CommonInParams {
    private String title;
    private String content;
    private String old_time;
    private String new_time;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getOld_time() { return old_time; }
    public String getNew_time() { return new_time; }
}
