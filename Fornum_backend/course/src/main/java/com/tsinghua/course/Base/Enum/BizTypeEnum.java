package com.tsinghua.course.Base.Enum;

import com.tsinghua.course.Biz.Controller.TestController;
import com.tsinghua.course.Biz.Controller.TimerController;
import com.tsinghua.course.Biz.Controller.UserController;

/**
 * @描述 业务类型枚举，所有的业务类型都需要枚举在此类中
 **/
public enum BizTypeEnum {
    /** 以下为用户业务类型 */
    USER_LOGIN(UserController.class, "用户登录"),

    /** 定时任务业务测试 */
    LOG_TEST(TimerController.class, "定时日志测试"),

    /** 测试业务，在书写正式代码时可以删除，在书写正式代码前先运行测试业务，如果测试业务无问题说明各模块正常 */
    LOGIN_TEST(TestController.class, "登录控制测试"),
    ADMIN_TEST(TestController.class, "管理员权限控制测试"),
    REDIS_TEST(TestController.class, "redis缓存测试"),
    TIMER_TEST(TestController.class, "定时器测试"),
    ERROR_TEST(TestController.class, "内部报错测试"),
    FILE_UPLOAD_TEST(TestController.class, "文件上传测试"),
    MULTI_RETURN_TEST(TestController.class, "返回多个参数的测试"),
    MONGODB_TEST(TestController.class, "mongodb数据库功能测试")
    ;

    BizTypeEnum(Class<?> controller, String description) {
        this.controller = controller;
        this.description = description;
    }

    /** 执行业务具体的类 */
    Class<?> controller;
    /** 业务描述 */
    String description;

    public Class<?> getControllerClass() {
        return controller;
    }

    public String getDescription() {
        return description;
    }
}
