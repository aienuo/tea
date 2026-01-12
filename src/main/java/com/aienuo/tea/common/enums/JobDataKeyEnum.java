package com.aienuo.tea.common.enums;

/**
 * Quartz Job Data 的 key 枚举
 */
public enum JobDataKeyEnum {

    /**
     * 任务标识
     */
    JOB_ID,

    /**
     * 任务处理器名字
     */
    JOB_HANDLER_NAME,

    /**
     * 任务处理器参数
     */
    JOB_HANDLER_PARAM,

    /**
     * 任务最大重试次数
     */
    JOB_RETRY_COUNT,

    /**
     * 任务每次重试间隔
     */
    JOB_RETRY_INTERVAL,

}
