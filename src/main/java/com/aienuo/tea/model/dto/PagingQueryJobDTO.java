package com.aienuo.tea.model.dto;

import com.aienuo.tea.common.base.BasePageDTO;
import com.aienuo.tea.common.enums.JobStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "定时任务-分页查询参数")
public class PagingQueryJobDTO extends BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

}
