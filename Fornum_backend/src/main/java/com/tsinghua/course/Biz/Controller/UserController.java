package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.LoginInParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserUtil.SignUpParams;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.*;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @描述 用户控制器，用于执行用户相关的业务
 **/
@Component
public class UserController {

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    RedisUtil redisUtil;

    /** 用户登录业务 */
    @BizType(BizTypeEnum.USER_LOGIN)
    public CommonOutParams userLogin(LoginInParams inParams) throws Exception {
        String email = inParams.getEmail();
        if (email == null)
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);
        String userID = inParams.getUserID();
        if (userID == null)
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);
        User user = userProcessor.getUserByEmail(email);
        if (user == null || !user.getPassword().equals(inParams.getPassword()))
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);

        /** 登录成功，记录登录状态 */
        ChannelHandlerContext ctx =  ThreadUtil.getCtx();
        /** ctx不为空记录WebSocket状态，否则记录http状态 */
        if (ctx != null)
            SocketUtil.setUserSocket(email, ctx);
        else {
            HttpSession httpSession = ThreadUtil.getHttpSession();
            if (httpSession != null) {
                httpSession.setUsername(email);
            }
        }
        return new CommonOutParams(true);
    }

    /** 用户注册业务 */
    @BizType(BizTypeEnum.USER_SIGNUP)
    public CommonOutParams userSignUp(SignUpParams inParams) throws Exception {
        System.out.println("userSignUp");
        String email = inParams.getEmail();
        if (email == null) {
            throw new CourseWarn(UserWarnEnum.SIGNUP_FAILED);
        }
        String userID = inParams.getUserID();
        if (userID == null) {
            throw new CourseWarn(UserWarnEnum.SIGNUP_FAILED);
        }
        String nickname = inParams.getNickname();

//        String code = inParams.getVerified_code();
//        if(!redisUtil.getString(phoneNumber).equals(code)) {
//            throw new CourseWarn(UserWarnEnum.INCORRECT_VERIFIEDCODE);
//        }
//        redisUtil.deleteKeys(phoneNumber);
        userProcessor.createUser(email, userID, nickname, inParams.getPassword());
        // userProcessor.addtime(inParams.getUsername());
        return new CommonOutParams(true);
    }

}
