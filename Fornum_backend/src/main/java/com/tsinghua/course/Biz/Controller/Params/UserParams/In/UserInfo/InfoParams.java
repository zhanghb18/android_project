package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserInfo;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.USER_MODIFYINFO)
public class InfoParams extends CommonInParams {
    @Required
    private String nickname;

    private String aboutMe;

    public String getNickname() { return nickname; }
    public void setNickname(String n) { this.nickname = n; }

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String a) { this.aboutMe = a; }

}
