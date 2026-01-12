package com.aienuo.tea.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务日志的状态枚举
 *
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JobLogStatusEnum implements IEnum<Integer> {

    SUCCESS(0, "成功"),

    FAILURE(1, "失败"),

    ;

    /**
     * 状态
     */
    @EnumValue
    private final Integer status;

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
     * @return JobLogStatusEnum - 任务日志的状态枚举
     */
    public static JobLogStatusEnum getEnum(final Integer status) {
        for (JobLogStatusEnum jobLogStatus : JobLogStatusEnum.values()) {
            if (jobLogStatus.getStatus().equals(status)) {
                return jobLogStatus;
            }
        }
        return null;
    }

}
