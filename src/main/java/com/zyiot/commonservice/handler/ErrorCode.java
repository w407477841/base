package com.zyiot.commonservice.handler;


public enum ErrorCode {
	Code400(404,"该地址不存在"),
	Code401(401,"登录失败"),
	Code403(403,"登录令牌失效"),
	Code500(500,"请检查请求参数[username][password]")
	;
private Integer code;
private String message;
private ErrorCode(Integer code, String message) {
	this.code = code;
	this.message = message;
}
public Integer getCode() {
	return code;
}
public String getMessage() {
	return message;
}
public static String msg(Integer code) {
    for (ErrorCode errorCode : ErrorCode.values()) {
        if (errorCode.getCode().equals(code)) {
            return errorCode.getMessage();
        }
    }
    return null;
}
	
}
