package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Enum.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

@BizType(BizTypeEnum.USER_BLOCKS)
public class BlocksOutParams extends CommonOutParams {
    private String blocks;

    public BlocksOutParams(String blocks) {
        this.blocks = blocks;
        this.success = true;
    }

    public String getBlocks() { return blocks; }
}
