package com.aienuo.tea.common.enums;

import com.aienuo.tea.common.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements CommonExceptionAssert {

    SUCCESS(200, "SUCCESS"),

    ERROR(9999, "ERROR"),

    /**
     * 服务器遇到错误，无法完成请求。服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    TOKEN_500(9998, "Token 过期"),
    PERMISSIONS_500(9997, "未授权"),
    NOT_IMPLEMENTED(9996, "功能未实现/未开启"),

    ;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

}
