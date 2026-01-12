package com.aienuo.tea.model.po;


import com.aienuo.tea.common.base.BaseEntity;
import com.aienuo.tea.common.enums.JobStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 定时任务
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_JOB")
public class Job extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 任务状态
     */
    @TableField(value = "STATUS")
    private JobStatusEnum status;

    /**
     * 处理器名字
     */
    @TableField(value = "HANDLER_NAME")
    private String handlerName;

    /**
     * 处理器参数
     */
    @TableField(value = "HANDLER_PARAM")
    private String handlerParam;

    /**
     * CRON 表达式
     */
    @TableField(value = "CRON_EXPRESSION")
    private String cronExpression;

    // ========== 重试相关字段 ==========
    /**
     * 重试次数 如果不重试，则设置为 0
     */
    @TableField(value = "RETRY_COUNT")
    private Integer retryCount;

    /**
     * 重试间隔，单位：毫秒 如果没有间隔，则设置为 0
     */
    @TableField(value = "RETRY_INTERVAL")
    private Integer retryInterval;


    // ========== 监控相关字段 ==========
    /**
     * 监控超时时间，单位：毫秒 为-1时，表示不监控
     * <p>
     * 注意，这里的超时的目的，不是进行任务的取消，而是告警任务的执行时间过长
     */
    @TableField(value = "MONITOR_TIMEOUT")
    private Integer monitorTimeout;

}
