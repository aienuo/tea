package com.aienuo.tea.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Quartz Cron 表达式的工具类
 *
 */
@Slf4j
public class CronUtils {

    /**
     * 校验 CRON 表达式是否有效
     *
     * @param cronExpression - CRON 表达式
     * @return Boolean - 是否有效
     */
    public static Boolean isValid(final String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 基于 CRON 表达式，获得下 n 个满足执行的时间
     *
     * @param cronExpression - CRON 表达式
     * @param number         - 数量
     * @return List<LocalDateTime> - 满足条件的执行时间
     */
    public static List<LocalDateTime> getNextTimeList(final String cronExpression, final Integer number) {
        // 获得 CronExpression 对象
        CronExpression cron;
        try {
            cron = new CronExpression(cronExpression);
        } catch (ParseException exception) {
            log.error("获得 CronExpression 对象异常：{}", exception.getMessage(), exception);
            throw new IllegalArgumentException(exception.getMessage());
        }
        // 从当前开始计算，n 个满足条件的
        Date now = new Date();
        List<LocalDateTime> nextTimeList = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Date nextTime = cron.getNextValidTimeAfter(now);
            // Date 转 LocalDateTime
            nextTimeList.add(nextTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            // 切换现在，为下一个触发时间；
            now = nextTime;
        }
        return nextTimeList;
    }

}
