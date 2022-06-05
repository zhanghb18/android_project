package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt.*;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.MomentOutParams;
import com.tsinghua.course.Biz.Processor.MomentProcessor;
import com.tsinghua.course.Frame.Util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MomentController {
    @Autowired
    MomentProcessor momentProcessor;

    @Autowired
    RedisUtil redisUtil;

//    @NeedLogin
    @BizType(BizTypeEnum.MOMENT_POST)
    public CommonOutParams postMoment(PostMomentInParams inParams) throws Exception {
        System.out.println("postMoment");
        momentProcessor.CreateMomentByUser(inParams);
        return new CommonOutParams(true);
    }

    @BizType(BizTypeEnum.MOMENT_GETPERSONAL)
    public MomentOutParams getPersonalMoment(PersonalMomentsInParams inParams) throws Exception {
        return new MomentOutParams(momentProcessor.getPersonalMomentByEmail(inParams.getUser_email()));
    }

//    @NeedLogin
    @BizType(BizTypeEnum.MOMENT_LIKE)
    public CommonOutParams likeMoment(LikeMomentInParams inParams) throws Exception {
        System.out.println("likeMoment");
        momentProcessor.LikeMoment(inParams);
        return new CommonOutParams(true);
    }

//    @NeedLogin
    @BizType(BizTypeEnum.MOMENT_CANCELLIKE)
    public CommonOutParams cancelLike(UnlikeMomentInParams inParams) throws Exception {
        momentProcessor.unlikeMomentByEmail(inParams);
        return new CommonOutParams(true);
    }

    @BizType(BizTypeEnum.MOMENT_GET)
    public MomentOutParams getMoments(CommonInParams inParams) throws Exception {
        System.out.println("getMoments");
        return new MomentOutParams(momentProcessor.getMoments(inParams.getEmail()));
    }

    @BizType(BizTypeEnum.MOMENT_GETSTARS)
    public MomentOutParams getStarMoments(CommonInParams inParams) throws Exception {
        return new MomentOutParams(momentProcessor.getStarMoments(inParams.getEmail()));
    }

    @BizType(BizTypeEnum.MOMENT_GETBYLIKES)
    public MomentOutParams getMomentsByLikes(CommonInParams inParams) throws Exception {
        return new MomentOutParams(momentProcessor.getMomentsByLikes(inParams.getEmail()));
    }

    @BizType(BizTypeEnum.MOMENT_GETSTARSBYLIKES)
    public MomentOutParams getStarMomentsByLikes(CommonInParams inParams) throws Exception {
        return new MomentOutParams(momentProcessor.getStarMomentsByLikes(inParams.getEmail()));
    }

//    @NeedLogin
    @BizType(BizTypeEnum.MOMENT_COMMENT)
    public CommonOutParams commentMoment(CommentMomentInParams inParams) throws Exception {
        momentProcessor.CommentMoment(inParams);
        return new CommonOutParams(true);
    }

//    @NeedLogin
    @BizType(BizTypeEnum.MOMENT_DELETECOMMENT)
    public CommonOutParams deleteComment(DeleteCommentInParams inParams) throws Exception {
        momentProcessor.DeleteComment(inParams);
        return new CommonOutParams(true);
    }
}
