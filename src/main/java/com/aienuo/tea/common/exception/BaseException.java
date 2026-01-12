package com.aienuo.tea.common.exception;

import com.aienuo.tea.common.enums.IResponseEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * 异常
 */
@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 异常枚举接口
     */
    protected IResponseEnum responseEnum;

    /**
     * 参数集合
     */
    protected Object[] args;

    /**
     * 异常
     *
     * @param responseEnum - 异常枚举
     */
    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    /**
     * 异常
     *
     * @param code    - 返回码
     * @param message - 返回信息
     */
    public BaseException(Integer code, String message) {
        super(message);
        this.responseEnum = new IResponseEnum() {
            @Override
            public Integer getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    /**
     * 异常
     *
     * @param responseEnum - 异常枚举
     * @param args         - 参数集合
     * @param message      - 返回信息
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    /**
     * 异常
     *
     * @param responseEnum - 异常枚举
     * @param args         - 参数集合
     * @param message      - 返回信息
     * @param throwable    - Throwable
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable throwable) {
        super(message, throwable);
        this.responseEnum = responseEnum;
        this.args = args;
    }

}
