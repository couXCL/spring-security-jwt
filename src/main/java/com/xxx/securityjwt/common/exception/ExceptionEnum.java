package com.xxx.securityjwt.common.exception;

/**
 * 异常枚举
 */
public enum ExceptionEnum {
    USERNAME_PASSWORD_ERROR(401,"用户名或密码错误"),
    ACCESS_DENIED(403,"此账号无权限"),
    NEED_LOGIN(401,"需要登陆");
    Integer code;
    String msg;//异常信息

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
