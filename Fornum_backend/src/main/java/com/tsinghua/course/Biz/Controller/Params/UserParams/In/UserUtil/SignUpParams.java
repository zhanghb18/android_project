package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserUtil;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

/**
 * @描述 用户注册的入参
 */
@BizType(BizTypeEnum.USER_SIGNUP)
public class SignUpParams extends CommonInParams{
    // 注册--邮箱
    @Required
    private String email;

//    // 注册--验证码
//    @Required
//    private String verified_code;

    // 注册--密码
    @Required
    private String password;

    public String getEmail() {
        return email;
    }
    public void setEmail(String e) {
        this.email = e;
    }

//    public String getVerified_code() {
//        return verified_code;
//    }
//    public void setVerified_code(String verified_code){
//        this.verified_code = verified_code;
//    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}

