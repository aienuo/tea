package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseEntity;
import com.aienuo.tea.common.enums.JobLogStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务执行日志
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_JOB_LOG")
public class JobLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    @TableField(value = "JOB_ID")
    private String jobId;

    /**
     * 处理器的名字
     */
    @TableField(value = "HANDLER_NAME")
    private String handlerName;

    /**
     * 处理器的参数
     */
    @TableField(value = "HANDLER_PARAM")
    private String handlerParam;

    /**
     * 第几次执行
     * <p>
     * 用于区分是不是重试执行。如果是重试执行，则 index 大于 1
     */
    @TableField(value = "EXECUTE_INDEX")
    private Integer executeIndex;

    /**
     * 开始执行时间
     */
    @TableField(value = "START_TIME")
    private LocalDateTime startTime;

    /**
     * 结束执行时间
     */
    @TableField(value = "END_TIME")
    private LocalDateTime endTime;

    /**
     * 执行时长，单位：毫秒
     */
    @TableField(value = "DURATION")
    private Long duration;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private JobLogStatusEnum status;

    /**
     * 结果数据
     */
    @TableField(value = "RESULT")
    private String result;

}
