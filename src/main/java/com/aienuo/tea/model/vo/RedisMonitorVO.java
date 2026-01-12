package com.aienuo.tea.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Data
@Schema(description = "Redis 监控信息")
public class RedisMonitorVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Redis info 指令结果,具体字段，查看 Redis 文档")
    private Properties info;

    @Schema(description = "Redis key 数量")
    private Long databaseSize;

    @Schema(description = "CommandStat 列表")
    private List<CommandStat> commandStatList;

    @Data
    @Schema(description = "Redis 命令统计结果")
    public static class CommandStat {

        @Schema(description = "Redis 命令")
        private String command;

        @Schema(description = "命令参数")
        private Map<String, Object> param;

    }

}
