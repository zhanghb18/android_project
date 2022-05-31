package com.tsinghua.course.Base.Error;

/**
 * @描述 用户操作警告枚举
 **/
public enum UserWarnEnum implements ExceptionInterface {
    LOGIN_FAILED("UserWarn001", "用户或密码不正常"),

    NEED_LOGIN("UserWarn002", "用户未登录或登录已过期"),

    PERMISSION_DENIED("UserWarn003", "无权限访问对应内容"),

    SIGNUP_FAILED("UserWarn004", "注册失败，请检查填写信息"),

    USERID_DOUBLED("UserWarn005", "用户ID已存在"),

    EMAIL_DOUBLED("UserWarn006", "该邮箱已被注册，请直接登录"),

    EMAIL_FAILED("UserWarn007", "未获取到用户邮箱"),

    MOMENT_FAILED("UserWarn008", "该条动态不存在"),

    STAR_FAILED("UserWarn009", "已经关注该用户，请勿重复关注"),

    USER_FAILED("UserWarn010", "用户不存在"),

    BLOCK_FAILED("UserWarn011", "已经屏蔽该用户，请勿重复屏蔽")
//
//    FILE_INVALID("UserWarn006", "文件不合法"),
//
//    INVALID_OPTION("UserWarn007", "操作不合法"),
//
//    INVALID_USERNAME("UserWarn008", "用户名不合法"),
//
//    INCORRECT_VERIFIEDCODE("UserWarn009", "验证码错误"),
    ;

    UserWarnEnum(String code, String msg) {
        errorCode = code;
        errorMsg = msg;
    }

    private String errorCode;
    private String errorMsg;
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMsg;
    }
}
