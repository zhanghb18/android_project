package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

public class BoolOutParams extends CommonOutParams {
    private boolean flag;

    public BoolOutParams(boolean flag) {
        this.flag = flag;
        this.success = true;
    }
}
