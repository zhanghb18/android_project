package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

public class DraftOutParams extends CommonOutParams {
    private String drafts;

    public DraftOutParams(String d) {
        this.drafts = d;
        this.success = true;
    }
}
