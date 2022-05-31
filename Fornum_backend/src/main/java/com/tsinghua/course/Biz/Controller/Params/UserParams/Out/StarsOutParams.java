package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

@BizType(BizTypeEnum.USER_STARS)
public class StarsOutParams extends CommonOutParams {
    private String stars;

    public StarsOutParams(String stars) {
        this.stars = stars;
    }

    public String getStars() {
        return stars;
    }
}
