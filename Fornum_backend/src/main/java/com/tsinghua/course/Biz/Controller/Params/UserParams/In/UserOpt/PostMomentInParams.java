package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.MOMENT_POST)
public class PostMomentInParams extends CommonInParams {
    // 发布时间 yyyy-MM-dd HH:mm:ss
    @Required
    private String post_time;

    private String title;

    private String content;

//    private MixedFileUpload[] images;

    public String getPost_time() {
        return post_time;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() { return title; }

//    public MixedFileUpload[] getImages() {
//        return images;
//    }
}
