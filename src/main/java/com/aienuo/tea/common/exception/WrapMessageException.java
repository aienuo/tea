package com.aienuo.tea.common.exception;

import java.io.Serial;

/**
 * 异常
 */
public class WrapMessageException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 异常
     *
     * @param message - 返回消息
     */
    public WrapMessageException(String message) {
        super(message);
    }

    /**
     * 异常
     *
     * @param message   - 返回消息
     * @param throwable - Throwable
     */
    public WrapMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
