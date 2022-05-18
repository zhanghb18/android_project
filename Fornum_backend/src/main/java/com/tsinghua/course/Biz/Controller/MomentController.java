package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt.*;
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
    @BizType(BizTypeEnum.USER_POSTMOMENT)
    public CommonOutParams postMoment(PostMomentInParams inParams) throws Exception {
        System.out.println("postMoment");
        momentProcessor.CreateMomentByUser(inParams);
        return new CommonOutParams(true);
    }

//    @NeedLogin
//    @BizType(BizTypeEnum.USER_LIKEMOMENT)
//    public CommonOutParams likeDiscover(LikeDiscoverInParams inParams) throws Exception {
//        momentProcessor.LikeDiscoverByUsername(inParams);
//        return new CommonOutParams(true);
//    }
//
//    @NeedLogin
//    @BizType(BizTypeEnum.USER_COMMENTMOMENT)
//    public CommonOutParams commentDiscover(CommentDiscoverInParams inParams) throws Exception {
//        momentProcessor.CommentDiscoverByUsername(inParams);
//        return new CommonOutParams(true);
//    }
//
//    @NeedLogin
//    @BizType(BizTypeEnum.USER_CANCELLIKE)
//    public CommonOutParams cancelLike(UnLikeDiscoverInParams inParams) throws Exception {
//        momentProcessor.unLikeDiscoverByUsername(inParams);
//        return new CommonOutParams(true);
//    }
//
//    @NeedLogin
//    @BizType(BizTypeEnum.USER_DELETECOMMENT)
//    public CommonOutParams deleteComment(DeleteCommentInParams inParams) throws Exception {
//        momentProcessor.deleteCommentByUsername(inParams);
//        return new CommonOutParams(true);
//    }
//
//    @NeedLogin
//    @BizType(BizTypeEnum.USER_GETDISCOVER)
//    public DiscoverOutParams getDiscover(CommonInParams inParams) throws Exception {
//        return new DiscoverOutParams(discoverProcessor.getDiscoverByUsername(inParams.getUsername()));
//    }
}
