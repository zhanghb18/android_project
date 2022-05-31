package com.tsinghua.course.Base.Enum;

import com.tsinghua.course.Biz.Controller.*;

/**
 * @描述 业务类型枚举，所有的业务类型都需要枚举在此类中
 **/
public enum BizTypeEnum {
    /** 以下为用户业务类型 */
    // Not Need Login
    USER_LOGIN(UserController.class, "/user/login", "用户登录"),
    USER_SIGNUP(UserController.class, "/user/signup", "用户注册"),
    // Do Need Login
    USER_PASSWORD(UserController.class, "/user/password", "用户修改密码"),
    USER_INFO(UserController.class, "/user/info", "用户基本信息获取"),
    USER_MODIFYINFO(UserController.class, "/user/modify_info", "用户基本信息修改"),
    USER_ADDSTAR(UserController.class, "/user/add_star", "用户关注"),
    USER_CANCELSTAR(UserController.class, "/user/cancel_star", "用户取消关注"),
    USER_ISSTAR(UserController.class, "/user/is_star", "判断用户是否为自己关注的用户"),
    USER_STARS(UserController.class, "/user/stars", "用户获取关注列表"),
    USER_BL0CK(UserController.class, "/user/block", "用户屏蔽"),
    USER_CANCELBL0CK(UserController.class, "/user/cancel_block", "用户取消屏蔽"),
    USER_ISBLOCK(UserController.class, "/user/is_block", "判断用户是否为自己屏蔽的用户"),

    USER_AVATAR(UserController.class, "/user/avatar", "用户头像设置"),
    USER_REMARK(UserController.class, "/user/remark", "修改用户备注"),
    USER_CHATLIST(UserController.class, "/user/chatlist", "用户获取会话列表"),
    USER_OPENCHAT(UserController.class, "/user/openchat", "用户打开新的会话列表"),
    USER_CREATEGROUP(UserController.class, "/user/creategroup", "创建群组"),
//    USER_INVITEGROUP(GroupController.class, "/user/invitegroup", "邀请好友入群"),
//    USER_DELETEMEMBER(GroupController.class, "/user/deletemember", "将指定好友踢出群聊"),
    USER_SENDMESSAGE(UserController.class, "/user/sendMessage", "用户发送信息"),
//    USER_GETCHAT(ChatController.class, "/user/getchat", "根据chat_id获取单个对话框的消息流"),
//    USER_GETCHATID(ChatController.class, "/user/getchatid", "用户获取chat_id"),
    USER_LOGOUT(UserController.class, "/user/logout", "用户注销"),

    /*Moment */
    MOMENT_POST(MomentController.class,"/moment/post",  "用户发布动态"),
    MOMENT_GETPERSONAL(MomentController.class, "/moment/get_personal", "用户个人主页动态的获取"),
    MOMENT_GET(MomentController.class, "/moment/get", "获取当前所有动态"),
    MOMENT_LIKE(MomentController.class, "/moment/like", "用户点赞动态"),
    MOMENT_CANCELLIKE(MomentController.class, "/moment/cancellike", "用户取消点赞"),
    MOMENT_COMMENT(MomentController.class, "/moment/comment", "用户评论动态/回复评论"),
    MOMENT_DELETECOMMENT(MomentController.class, "/moment/delete_comment", "用户删除评论/回复"),

    /** 定时任务业务测试 */
    LOG_TEST(TimerController.class, null, "定时日志测试"),
    FILE_TEST(UserController.class, "/test/file", "文件存储测试"),

    /** 测试业务，在书写正式代码时可以删除，在书写正式代码前先运行测试业务，如果测试业务无问题说明各模块正常 */
    LOGIN_TEST(TestController.class, "/test/loginPermission", "登录控制测试"),
    ADMIN_TEST(TestController.class, "/test/adminPermission", "管理员权限控制测试"),
    REDIS_TEST(TestController.class, "/test/redis", "redis缓存测试"),
    TIMER_TEST(TestController.class, "/test/timer", "定时器测试"),
    ERROR_TEST(TestController.class, "/test/error", "内部报错测试"),
    FILE_UPLOAD_TEST(TestController.class, "/test/upload", "文件上传测试"),
    FILE_DOWNLOAD_TEST(TestController.class, "/test/url", "获取文件下载的路径"),
    MULTI_RETURN_TEST(TestController.class, "/test/multiParams", "返回多个参数的测试"),
    MONGODB_TEST(TestController.class, "/test/mongodb", "mongodb数据库功能测试")
    ;

    BizTypeEnum(Class<?> controller, String httpPath, String description) {
        this.controller = controller;
        this.description = description;
        this.httpPath = httpPath;
    }

    /** 执行业务具体的类 */
    Class<?> controller;
    /** 业务对应的http请求路径 */
    String httpPath;
    /** 业务描述 */
    String description;

    public Class<?> getControllerClass() {
        return controller;
    }

    public String getDescription() {
        return description;
    }

    public String getHttpPath() {
        return httpPath;
    }
}
