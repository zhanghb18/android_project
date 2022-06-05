package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.LoginInParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserInfo.InfoParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserInfo.PasswordParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt.*;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserUtil.SignUpParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.*;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.*;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @描述 用户控制器，用于执行用户相关的业务
 **/
@Component
public class UserController {

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    RedisUtil redisUtil;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /** 用户登录业务 */
    @BizType(BizTypeEnum.USER_LOGIN)
    public CommonOutParams userLogin(LoginInParams inParams) throws Exception {
        System.out.println("userLogin");
        String email = inParams.getEmail();
        if (email == null)
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);
        User user = userProcessor.getUserByEmail(email);
        if (user == null || !passwordEncoder.matches(inParams.getPassword(), user.getPassword()))
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
//         userProcessor.addtime(inParams.getUsername());
        return new CommonOutParams(true);
    }

    /** 获取用户信息 */
//    @NeedLogin
    @BizType(BizTypeEnum.USER_INFO)
    public UserInfoOutParams userInfo(CommonInParams inParams) throws Exception {
        System.out.println("userInfo");
        UserInfoOutParams res = null;
        if (inParams.getEmail() != null) {
            System.out.println(inParams.getEmail());
            res = userProcessor.getUserInfo(inParams.getEmail());
            return res;
        }
        else {
            System.out.println("null");
            throw new CourseWarn(UserWarnEnum.EMAIL_FAILED);
        }
    }

    /** 用户修改密码 */
    @BizType(BizTypeEnum.USER_PASSWORD)
    public CommonOutParams userPassword(PasswordParams inParams) throws Exception {
        System.out.println("userPassword");
        String email = inParams.getEmail();
        if (email == null) {
            throw new CourseWarn(UserWarnEnum.EMAIL_FAILED);
        }
        userProcessor.modifyPassword(email, inParams.getPassword());
        return new CommonOutParams(true);
    }

    /** 用户修改基本信息 */
    @BizType(BizTypeEnum.USER_MODIFYINFO)
    public CommonOutParams userModifyInfo(InfoParams inParams) throws Exception {
        System.out.println("userModifyInfo");
        String email = inParams.getEmail();
        if (email == null) {
            throw new CourseWarn(UserWarnEnum.EMAIL_FAILED);
        }
        userProcessor.modifyInfo(email, inParams.getUserID(), inParams.getNickname(), inParams.getAboutMe());
        return new CommonOutParams(true);
    }

    /** 用户关注 */
//    @NeedLogin
    @BizType(BizTypeEnum.USER_ADDSTAR)
    public CommonOutParams userAddStar(StarAddInParams inParams) throws Exception {
        userProcessor.addStar(inParams.getEmail(), inParams.getStar_email());
        return new CommonOutParams(true);
    }

    /** 用户取消关注 */
//    @NeedLogin
    @BizType(BizTypeEnum.USER_CANCELSTAR)
    public CommonOutParams userCancelStar(StarCancelInParams inParams) throws Exception {
        userProcessor.cancelStar(inParams.getEmail(), inParams.getCancel_email());
        return new CommonOutParams(true);
    }

    /** 判断是否为用户关注的人 */
    @BizType(BizTypeEnum.USER_ISSTAR)
    public BoolOutParams isStar(IsStarInParams inParams) throws Exception {
        return new BoolOutParams(userProcessor.isStar(inParams.getEmail(), inParams.getUser_email()));
    }

    /** 用户获取关注列表 */
    @BizType(BizTypeEnum.USER_STARS)
    public StarsOutParams getStars(CommonInParams inParams) throws Exception {
        String res = userProcessor.getStars(inParams.getEmail());
        return new StarsOutParams(res);
    }

    /** 用户屏蔽 */
    @BizType(BizTypeEnum.USER_BL0CK)
    public CommonOutParams blockUser(BlockInParams inParams) throws Exception {
        userProcessor.blockUser(inParams.getEmail(), inParams.getBlock_email());
        return new CommonOutParams(true);
    }

    /** 用户取消屏蔽 */
    @BizType(BizTypeEnum.USER_CANCELBL0CK)
    public CommonOutParams userCancelBlock(BlockCancelInParams inParams) throws Exception {
        userProcessor.cancelBlock(inParams.getEmail(), inParams.getCancel_email());
        return new CommonOutParams(true);
    }

    /** 判断是否为用户屏蔽的人 */
    @BizType(BizTypeEnum.USER_ISBLOCK)
    public BoolOutParams isBlock(IsBlockInParams inParams) throws Exception {
        return new BoolOutParams(userProcessor.isBlock(inParams.getEmail(), inParams.getUser_email()));
    }

    /** 用户获取黑名单列表 */
    @BizType(BizTypeEnum.USER_BLOCKS)
    public BlocksOutParams getBlocks(CommonInParams inParams) throws Exception {
        String res = userProcessor.getBlocks(inParams.getEmail());
        return new BlocksOutParams(res);
    }

    /** 获取用户通知列表 */
    @BizType(BizTypeEnum.USER_NOTICE)
    public NoticeOutParams getNotice(CommonInParams inParams) throws Exception {
        String res = userProcessor.getNotice(inParams.getEmail());
        return new NoticeOutParams(res);
    }

    /** 将通知设置为已读 */
    @BizType(BizTypeEnum.USER_NOTICEREAD)
    public CommonOutParams setNoticeRead(NoticeReadInParams inParams) throws Exception {
        userProcessor.setNoticeRead(inParams.getEmail(), inParams.getNotice_time());
        return new CommonOutParams(true);
    }

    /** 新增草稿 */
    @BizType(BizTypeEnum.USER_ADDDRAFT)
    public CommonOutParams addDraft(AddDraftInParams inParams) throws Exception {
        userProcessor.addDraft(inParams.getEmail(), inParams.getTitle(), inParams.getContent(), inParams.getTime());
        return new CommonOutParams(true);
    }

    /** 修改草稿 */
    @BizType(BizTypeEnum.USER_MODIFYDRAFT)
    public CommonOutParams modifyDraft(ModifyDraftInParams inParams) throws Exception {
        userProcessor.modifyDraft(inParams.getEmail(), inParams.getTitle(), inParams.getContent(), inParams.getOld_time(), inParams.getNew_time());
        return new CommonOutParams(true);
    }

    /** 删除草稿 */
    // TODO

    /** 发布草稿 */
    @BizType(BizTypeEnum.USER_POSTDRAFT)
    public CommonOutParams postDraft(PostDraftInParams inParams) throws Exception {
        userProcessor.postDraft(inParams.getEmail(), inParams.getTitle(), inParams.getContent(), inParams.getOld_time(), inParams.getPost_time());
        return new CommonOutParams(true);
    }

    /** 获取草稿箱列表 */
    @BizType(BizTypeEnum.USER_DRAFTS)
    public CommonOutParams getDrafts(CommonInParams inParams) throws Exception {
        String res = userProcessor.getDrafts(inParams.getEmail());
        return new DraftOutParams(res);
    }
}
