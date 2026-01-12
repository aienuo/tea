package com.aienuo.tea.model.vo;

import com.aienuo.tea.common.enums.JobStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "定时任务-返回值")
public class JobVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务编号")
    private String id;

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "任务状态")
    private JobStatusEnum status;

    @Schema(description = "处理器名字")
    private String handlerName;

    @Schema(description = "处理器参数")
    private String handlerParam;

    @Schema(description = " CRON 表达式")
    private String cronExpression;

    @Schema(description = "重试次数 如果不重试，则设置为 0")
    private Integer retryCount;

    @Schema(description = "重试间隔（单位：毫秒） 如果没有间隔，则设置为 0")
    private Integer retryInterval;

    @Schema(description = "监控超时时间（单位：毫秒） 为-1时，表示不监控")
    private Integer monitorTimeout;

    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
