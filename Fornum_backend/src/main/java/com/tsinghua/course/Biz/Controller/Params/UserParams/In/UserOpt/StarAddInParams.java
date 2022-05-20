package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_ADDSTAR)
public class StarAddInParams extends CommonInParams {
    @Required
    // 要关注的用户
    private String star_email;


    public String getStar_email() {
        return star_email;
    }

    public void setStar_email(String s) {
        this.star_email = s;
    }
}

