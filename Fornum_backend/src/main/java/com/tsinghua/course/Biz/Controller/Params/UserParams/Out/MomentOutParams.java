package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

public class MomentOutParams extends CommonOutParams {
    private String moments;

    public MomentOutParams(String m) {
        this.moments = m;
    }
}
