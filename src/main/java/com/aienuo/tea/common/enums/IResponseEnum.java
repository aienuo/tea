package com.aienuo.tea.common.enums;

/**
 * 异常枚举接口
 */
public interface IResponseEnum {

    /**
     * 获取返回码
     *
     * @return 返回码
     */
    Integer getCode();

    /**
     * 获取返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}
