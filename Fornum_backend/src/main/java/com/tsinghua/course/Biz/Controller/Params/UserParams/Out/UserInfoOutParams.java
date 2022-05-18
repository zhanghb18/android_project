package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

/* 获取用户信息 */
@BizType(BizTypeEnum.USER_INFO)
public class UserInfoOutParams extends CommonOutParams {
    String userInfo;
    String avatar;

    public UserInfoOutParams(String userInfo, String avatar) {
        this.userInfo = userInfo;
        this.avatar = avatar;
        this.success = true;
    }
    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
