package com.tsinghua.course.Biz.Controller.Params.UserParams.In.UserOpt;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import io.netty.handler.codec.http.multipart.MixedFileUpload;
import org.springframework.beans.factory.annotation.Required;

@BizType(BizTypeEnum.USER_POSTMOMENT)
public class PostMomentInParams extends CommonInParams {
    // 发布时间 yyyy-MM-dd HH:mm:ss
    private String post_time;

    private String content;

    private MixedFileUpload[] images;

    public String getPost_time() {
        return post_time;
    }

    public String getContent() {
        return content;
    }

    public MixedFileUpload[] getImages() {
        return images;
    }
}
