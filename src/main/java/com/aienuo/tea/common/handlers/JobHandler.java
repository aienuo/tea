package com.aienuo.tea.common.handlers;

/**
 * 任务处理器 接口
 *
 */
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param - 参数
     * @return String - 结果
     * @throws Exception - 异常
     */
    String execute(String param) throws Exception;

}
