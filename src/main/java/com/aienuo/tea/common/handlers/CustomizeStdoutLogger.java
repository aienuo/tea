package com.aienuo.tea.common.handlers;

import com.p6spy.engine.spy.appender.StdoutLogger;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志输出
 */
@Slf4j
public class CustomizeStdoutLogger extends StdoutLogger {

    /**
     * 日志输出
     *
     * @param text - 日志信息
     */
    public void logText(String text) {
        log.error(text);
    }

}