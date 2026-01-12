package com.aienuo.tea.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 异步并行处理 工具类</br>
 *
 * LatchUtils 的设计哲学是：多次提交，一次等待。</br>
 *
 * <p>任务注册：在主流程代码中，可以先通过 LatchUtils.submitTask() 提交 Runnable 任务和其对应的 Executor（该线程池用来执行这个 Runnable）。</p>
 * <p>执行并等待：当并行任务都提交完毕后，你只需调用一次 LatchUtils.waitFor()。该方法会立即触发所有已注册任务的执行，并阻塞等待所有任务执行完成或超时。</p>
 */
@Slf4j
public class LatchUtils {

    /**
     * 线程池
     */
    private static final ThreadLocal<List<TaskInfo>> THREADLOCAL = ThreadLocal.withInitial(LinkedList::new);

    /**
     * 提交异步任务
     *
     * @param executor - 执行任务的线程池
     * @param runnable - 需要异步执行的具体业务逻辑
     */
    public static void submitTask(Executor executor, Runnable runnable) {
        THREADLOCAL.get().add(new TaskInfo(executor, runnable));
    }

    /**
     * 建构任务列表
     *
     * @return TaskInfo - 任务列表
     */
    private static List<TaskInfo> constructTask() {
        // 获取 任务列表
        List<TaskInfo> taskList = THREADLOCAL.get();
        // 清理数据
        THREADLOCAL.remove();
        return taskList;
    }

    /**
     * 触发所有已提交任务的执行，并同步等待它们全部完成
     *
     * @param timeout  - 最长等待时间
     * @param timeUnit - 等待时间单位
     * @return Boolean - 所有任务在指定时间内成功完成
     */
    public static Boolean waitFor(final Long timeout, final TimeUnit timeUnit) {
        // 建构任务列表
        List<TaskInfo> taskList = constructTask();
        if (taskList.isEmpty()) {
            return true;
        }
        // 初始化 CountDownLatch，数量为任务数
        CountDownLatch latch = new CountDownLatch(taskList.size());
        for (TaskInfo task : taskList) {
            Executor executor = task.executor;
            Runnable runnable = task.runnable;
            executor.execute(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                }
            });
        }
        boolean await = Boolean.FALSE;
        try {
            // 执行
            await = latch.await(timeout, timeUnit);
        } catch (Exception exception) {
            log.error("任务执行异常：{}", exception.getMessage(), exception);
        }
        return await;
    }

    /**
     * 任务信息
     *
     * @param executor 执行任务的线程池
     * @param runnable 需要异步执行的具体业务逻辑
     */

    private record TaskInfo(Executor executor, Runnable runnable) {
    }

}
