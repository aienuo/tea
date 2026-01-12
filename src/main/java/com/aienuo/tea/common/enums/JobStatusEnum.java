package com.aienuo.tea.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quartz.impl.jdbcjobstore.Constants;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 任务状态枚举
 *
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JobStatusEnum implements IEnum<Integer> {

    /**
     * 开启
     */
    NORMAL(0, Stream.of(Constants.STATE_WAITING, Constants.STATE_ACQUIRED, Constants.STATE_BLOCKED).collect(Collectors.toSet()), "开启"),

    /**
     * 暂停
     */
    STOP(1, Stream.of(Constants.STATE_PAUSED, Constants.STATE_PAUSED_BLOCKED).collect(Collectors.toSet()), "暂停"),

    ;

    /**
     * 状态
     */
    @EnumValue
    private final Integer status;

    /**
     * 对应的 Quartz 触发器的状态集合
     */
    private final Set<String> quartzState;

    /**
     * 描述
     */
    private final String label;

    /**
     * 枚举数据库存储值
     */
    @Override
    public Integer getValue() {
        return this.getStatus();
    }

    /**
     * 获取 枚举
     *
     * @param status - 状态
     * @return JobStatusEnum - 任务状态枚举
     */
    public static JobStatusEnum getEnum(final Integer status) {
        for (JobStatusEnum jobStatus : JobStatusEnum.values()) {
            if (jobStatus.getStatus().equals(status)) {
                return jobStatus;
            }
        }
        return null;
    }

}
