package com.aienuo.tea.common;

import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.common.enums.JobDataKeyEnum;
import com.aienuo.tea.common.handlers.JobHandlerInvoker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * {@link Scheduler 调度器} 的管理器，负责创建任务
 * <p>
 * 考虑到实现的简洁性，我们使用 jobHandlerName 作为唯一标识，即：
 * 1. Job 的 {@link JobDetail#getKey()}
 * 2. Trigger 的 {@link Trigger#getKey()}
 * <p>
 * 另外，jobHandlerName 对应到 Spring Bean 的名字，直接调用
 *
 * @param scheduler - 调度器 Scheduler
 */
@Slf4j
@Service
public record SchedulerManager(Scheduler scheduler) {

    /**
     * 建构触发器 Trigger
     *
     * @param handlerName    - 任务处理器的名字
     * @param handlerParam   - 任务处理器的参数
     * @param cronExpression - CRON 表达式
     * @param retryCount     - 重试次数
     * @param retryInterval  - 重试间隔
     * @return Trigger
     */
    private Trigger buildTrigger(String handlerName, String handlerParam, String cronExpression, Integer retryCount, Integer retryInterval) {
        return TriggerBuilder.newTrigger()
                .withIdentity(handlerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(JobDataKeyEnum.JOB_HANDLER_PARAM.name(), handlerParam)
                .usingJobData(JobDataKeyEnum.JOB_RETRY_COUNT.name(), retryCount)
                .usingJobData(JobDataKeyEnum.JOB_RETRY_INTERVAL.name(), retryInterval)
                .build();
    }

    /**
     * 添加 Job 到 Quartz 中
     *
     * @param jobId          - 任务编号
     * @param handlerName    - 任务处理器名字
     * @param handlerParam   - 任务处理器参数
     * @param cronExpression - CRON 表达式
     * @param retryCount     - 重试次数
     * @param retryInterval  - 重试间隔
     * @throws SchedulerException -  添加异常
     */
    public void insert(String jobId, String handlerName, String handlerParam, String cronExpression, Integer retryCount, Integer retryInterval) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        // 创建 JobDetail 对象
        JobDetail jobDetail = JobBuilder.newJob(JobHandlerInvoker.class)
                .usingJobData(JobDataKeyEnum.JOB_ID.name(), jobId)
                .usingJobData(JobDataKeyEnum.JOB_HANDLER_NAME.name(), handlerName)
                .withIdentity(handlerName).build();
        // 创建 触发器 Trigger 对象
        Trigger trigger = this.buildTrigger(handlerName, handlerParam, cronExpression, retryCount, retryInterval);
        // 新增调度
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 更新 Job 到 Quartz
     *
     * @param handlerName    - 任务处理器名字
     * @param handlerParam   - 任务处理器参数
     * @param cronExpression - CRON 表达式
     * @param retryCount     - 重试次数
     * @param retryInterval  - 重试间隔
     * @throws SchedulerException - 更新异常
     */
    public void update(String handlerName, String handlerParam, String cronExpression, Integer retryCount, Integer retryInterval) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        // 创建新 触发器 Trigger 对象
        Trigger newTrigger = this.buildTrigger(handlerName, handlerParam, cronExpression, retryCount, retryInterval);
        // 修改调度
        scheduler.rescheduleJob(new TriggerKey(handlerName), newTrigger);
    }

    /**
     * 删除 Quartz 中的 Job
     *
     * @param handlerName - 任务处理器名字
     * @throws SchedulerException - 删除异常
     */
    public void delete(String handlerName) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        scheduler.deleteJob(new JobKey(handlerName));
    }

    /**
     * 暂停 Quartz 中的 Job
     *
     * @param handlerName - 任务处理器名字
     * @throws SchedulerException - 暂停异常
     */
    public void pause(String handlerName) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        scheduler.pauseJob(new JobKey(handlerName));
    }

    /**
     * 启动 Quartz 中的 Job
     *
     * @param handlerName - 任务处理器名字
     * @throws SchedulerException 启动异常
     */
    public void resume(String handlerName) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        scheduler.resumeJob(new JobKey(handlerName));
        scheduler.resumeTrigger(new TriggerKey(handlerName));
    }

    /**
     * 立即触发一次 Quartz 中的 Job
     *
     * @param jobId        - 任务标识
     * @param handlerName  - 任务处理器名字
     * @param handlerParam - 任务处理器参数
     * @throws SchedulerException - 触发异常
     */
    public void trigger(String jobId, String handlerName, String handlerParam) throws SchedulerException {
        ArgumentResponseEnum.NULL_POINTER_EXCEPTION.assertNotNull(scheduler, "调度器 Scheduler 为 Null");
        JobDataMap data = new JobDataMap();
        // 无需重试，所以不设置 retryCount 和 retryInterval
        data.put(JobDataKeyEnum.JOB_ID.name(), jobId);
        data.put(JobDataKeyEnum.JOB_HANDLER_NAME.name(), handlerName);
        data.put(JobDataKeyEnum.JOB_HANDLER_PARAM.name(), handlerParam);
        // 触发任务
        scheduler.triggerJob(new JobKey(handlerName), data);
    }

}
