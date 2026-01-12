package com.aienuo.tea.common.handlers;

import com.aienuo.tea.common.enums.JobDataKeyEnum;
import com.aienuo.tea.common.enums.JobLogStatusEnum;
import com.aienuo.tea.model.po.JobLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 基础 Job 调用者，负责调用 {@link JobHandler#execute(String)} 执行任务
 *
 */
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@RequiredArgsConstructor
public class JobHandlerInvoker extends QuartzJobBean {

    /**
     * ApplicationContext
     */
    private final ApplicationContext applicationContext;

    /**
     * 执行
     *
     * @param handlerName  - 任务处理器的名字
     * @param handlerParam - 任务处理器的参数
     * @return String - 执行返回数据
     * @throws Exception - 异常
     */
    private String executeInternal(final String handlerName, final String handlerParam) throws Exception {
        // 获得 JobHandler 对象
        JobHandler jobHandler = applicationContext.getBean(handlerName, JobHandler.class);
        Assert.notNull(jobHandler, "JobHandler 不会为空");
        // 执行任务
        return jobHandler.execute(handlerParam);
    }

    /**
     * 异常处理器
     *
     * @param exception     - 执行异常 Throwable
     * @param refireCount   - 重试次数
     * @param retryCount    - 任务最大重试次数
     * @param retryInterval - 任务每次重试间隔
     * @throws JobExecutionException - 异常
     */
    private void handleException(Throwable exception, final Integer refireCount, final Integer retryCount, final Integer retryInterval) throws JobExecutionException {
        // 如果有异常，则进行重试
        if (exception == null) {
            return;
        }
        // 情况一：如果到达重试上限，则直接抛出异常即可
        if (refireCount >= retryCount) {
            throw new JobExecutionException(exception);
        }

        // 情况二：如果未到达重试上限，则 sleep 一定间隔时间，然后重试
        // 这里使用 sleep 来实现，主要还是希望实现比较简单。因为，同一时间，不会存在大量失败的 Job。
        if (retryInterval > 0) {
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                log.error("设置间隔时间失败：{}", e.getMessage());
            }
        }
        // 第二个参数，refireImmediately = true，表示立即重试
        throw new JobExecutionException(exception, true);
    }

    @Override
    protected void executeInternal(JobExecutionContext executionContext) throws JobExecutionException {
        LocalDateTime startTime = LocalDateTime.now();
        // 第一步，获得 Job 数据
        // 任务标识
        String jobId = executionContext.getMergedJobDataMap().getString(JobDataKeyEnum.JOB_ID.name());
        // 任务处理器名字
        String jobHandlerName = executionContext.getMergedJobDataMap().getString(JobDataKeyEnum.JOB_HANDLER_NAME.name());
        // 任务处理器参数
        String jobHandlerParam = executionContext.getMergedJobDataMap().getString(JobDataKeyEnum.JOB_HANDLER_PARAM.name());
        // 重试次数
        int refireCount = executionContext.getRefireCount();
        // 任务最大重试次数
        int retryCount = (Integer) executionContext.getMergedJobDataMap().getOrDefault(JobDataKeyEnum.JOB_RETRY_COUNT.name(), 0);
        // 任务每次重试间隔
        int retryInterval = (Integer) executionContext.getMergedJobDataMap().getOrDefault(JobDataKeyEnum.JOB_RETRY_INTERVAL.name(), 0);

        // 结果数据
        String data;
        Throwable exception = null;
        // Job 日志（初始）
        JobLog runningLog = new JobLog().setJobId(jobId).setHandlerName(jobHandlerName)
                .setHandlerParam(jobHandlerParam).setExecuteIndex(refireCount + 1)
                .setStartTime(startTime);
        // 处理是否成功
        boolean success = Boolean.FALSE;
        try {
            // 第二步，执行任务
            data = this.executeInternal(jobHandlerName, jobHandlerParam);
            success = Boolean.TRUE;
        } catch (Throwable throwable) {
            exception = throwable;
            // 记录错误信息
            data = exception.getMessage();
        }

        // 第三步，记录执行日志
        LocalDateTime endTime = LocalDateTime.now();
        runningLog.setEndTime(endTime).setDuration(Duration.between(startTime, endTime).toMillis())
                .setStatus(success ? JobLogStatusEnum.SUCCESS : JobLogStatusEnum.FAILURE).setResult(data);
        runningLog.insert();

        // 第四步，处理有异常的情况
        handleException(exception, refireCount, retryCount, retryInterval);
    }

}
