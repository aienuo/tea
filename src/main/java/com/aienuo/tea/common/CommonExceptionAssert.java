package com.aienuo.tea.common;

import com.aienuo.tea.common.enums.IResponseEnum;
import com.aienuo.tea.common.exception.BaseException;

import java.text.MessageFormat;

/**
 * 异常断言
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

    /**
     * <p> 创建 BaseException </p>
     *
     * @param args - 参数列表
     * @return BaseException - 基础异常类
     */
    @Override
    default BaseException newException(Object... args) {
        String msg = this.getMessage();
        if (args != null && args.length != 0) {
            msg = MessageFormat.format(this.getMessage(), args);
        }
        return new BaseException(this, args, msg);
    }

    /**
     * <p> 创建 BaseException </p>
     *
     * @param throwable - Throwable
     * @param args      - 参数列表
     * @return BaseException - 基础异常类
     */
    @Override
    default BaseException newException(Throwable throwable, Object... args) {
        String message = this.getMessage();
        if (args != null && args.length != 0) {
            message = MessageFormat.format(this.getMessage(), args);
        }
        return new BaseException(this, args, message, throwable);
    }

}
