package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加
 */
@Data
@Schema(description = "定时任务添加")
public class JobAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "任务名称不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "任务名称不能为空")
    private String name;

    /**
     * 处理器名字
     */
    @Schema(description = "处理器名字", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "处理器名字不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "处理器名字不能为空")
    private String handlerName;

    /**
     * 处理器参数
     */
    @Schema(description = "处理器参数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "处理器参数不能为空")
    private String handlerParam;

    /**
     * CRON 表达式
     */
    @Schema(description = "CRON 表达式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "CRON 表达式不能为空")
    private String cronExpression;

    /**
     * 重试次数 如果不重试，则设置为 0
     */
    @Schema(description = "重试次数")
    private Integer retryCount;

    /**
     * 重试间隔，单位：毫秒 如果没有间隔，则设置为 0
     */
    @Schema(description = "重试间隔（单位：毫秒）")
    private Integer retryInterval;

    /**
     * 监控超时时间，单位：毫秒 为-1时，表示不监控；注意，这里的超时的目的，不是进行任务的取消，而是告警任务的执行时间过长
     */
    @Schema(description = "监控超时时间（单位：毫秒）")
    private Integer monitorTimeout;

}
