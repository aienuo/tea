package com.aienuo.tea.business;

import com.aienuo.tea.common.SchedulerManager;
import com.aienuo.tea.common.base.BaseBusiness;
import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.common.enums.JobStatusEnum;
import com.aienuo.tea.model.converter.JobConverter;
import com.aienuo.tea.model.dto.JobAddDTO;
import com.aienuo.tea.model.dto.JobUpdateDTO;
import com.aienuo.tea.model.dto.PagingQueryJobDTO;
import com.aienuo.tea.model.dto.PagingQueryJobLogDTO;
import com.aienuo.tea.model.po.Job;
import com.aienuo.tea.model.vo.JobLogVO;
import com.aienuo.tea.model.vo.JobVO;
import com.aienuo.tea.service.IJobLogService;
import com.aienuo.tea.service.IJobService;
import com.aienuo.tea.utils.CronUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 定时任务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobBusiness extends BaseBusiness {

    /**
     * 定时任务 服务类
     */
    private final IJobService jobService;

    /**
     * Scheduler 管理器
     */
    private final SchedulerManager schedulerManager;

    /**
     * 定时任务执行日志 服务类
     */
    private final IJobLogService jobLogService;

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<JobLogVO> - 返回值
     */
    public Page<JobVO> pagingQueryListByParameter(final PagingQueryJobDTO pagingQuery) {
        // 查询
        Page<JobVO> pagingQueryList = this.jobService.pagingQueryListByParameter(pagingQuery);
        return pagingQueryList;
    }

    /**
     * 添加
     *
     * @param create - 添加对象
     * @return Boolean - 是否成功
     * @throws SchedulerException - 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(final JobAddDTO create) throws SchedulerException {
        ArgumentResponseEnum.COMMON_EXCEPTION.assertIsTrue(CronUtils.isValid(create.getCronExpression()), "CRON 表达式无效");
        // 校验唯一性
        Job job = this.jobService.getOne(Wrappers.<Job>lambdaQuery().eq(Job::getHandlerName, create.getHandlerName()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(job, "定时任务", "处理器名字重复");
        // 获取数据库添加对象
        job = JobConverter.INSTANCE.getAddEntity(create);
        // 添加 Job 到 Quartz 中
        this.schedulerManager.insert(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(), job.getRetryCount(), job.getRetryInterval());
        // 添加
        return this.jobService.save(job);
    }

    /**
     * 更新
     *
     * @param update - 更新对象
     * @return Boolean - 是否成功
     * @throws SchedulerException - 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(final JobUpdateDTO update) throws SchedulerException {
        ArgumentResponseEnum.COMMON_EXCEPTION.assertIsTrue(CronUtils.isValid(update.getCronExpression()), "CRON 表达式无效");
        // 校验存在
        Job job = this.jobService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(job, "定时任务", update.getId() + " 定时任务不存在");
        // 只有开启状态，才可以修改.原因是，如果出暂停状态，修改 Quartz Job 时，会导致任务又开始执行
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(JobStatusEnum.NORMAL.equals(job.getStatus()), "定时任务", "只有开启状态，才可以修改");
        // 获取数据库更新对象
        JobConverter.INSTANCE.getUpdateEntity(job, update);
        // 更新 Job 到 Quartz 中
        this.schedulerManager.update(job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(), job.getRetryCount(), job.getRetryInterval());
        // 更新
        return this.jobService.updateById(job);
    }

    /**
     * 删除
     *
     * @param id - 标识
     * @throws SchedulerException - 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(final String id) throws SchedulerException {
        // 校验存在
        Job job = this.jobService.getById(id);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertNotNull(job, "定时任务", id + " 定时任务不存在");
        // 删除 Job 到 Quartz 中
        this.schedulerManager.delete(job.getHandlerName());
        // 删除
        return this.jobService.removeById(id);
    }

    /**
     * 更新状态
     *
     * @param id     - 标识
     * @param status - JobStatusEnum
     * @return Boolean - 是否成功
     * @throws SchedulerException - 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateJobStatus(final String id, final Integer status) throws SchedulerException {
        JobStatusEnum statusEnum = JobStatusEnum.getEnum(status);
        // 校验 status
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(Arrays.asList(JobStatusEnum.NORMAL, JobStatusEnum.STOP).contains(statusEnum), "定时任务", " 定时任务状态不正确");
        // 校验存在
        Job job = this.jobService.getById(id);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(job, "定时任务", id + " 定时任务不存在");
        // 校验是否已经为当前状态
        if (!job.getStatus().equals(statusEnum)) {
            job.setStatus(statusEnum);
            // 更新状态 Job 到 Quartz 中
            if (JobStatusEnum.NORMAL.equals(statusEnum)) {
                // 开启
                this.schedulerManager.resume(job.getHandlerName());
            } else {
                // 暂停
                this.schedulerManager.pause(job.getHandlerName());
            }
            // 更新状态
            return this.jobService.updateById(job);
        }
        // 返回
        return Boolean.TRUE;
    }

    /**
     * 触发执行
     *
     * @param id - 标识
     * @throws SchedulerException - 异常
     */
    public void trigger(final String id) throws SchedulerException {
        // 校验存在
        Job job = this.jobService.getById(id);
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertNotNull(job, "定时任务", id + " 定时任务不存在");
        // 触发 Quartz 中的 Job
        this.schedulerManager.trigger(job.getId(), job.getHandlerName(), job.getHandlerParam());
    }

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page < JobLogVO> - 返回值
     */
    public Page<JobLogVO> pagingQueryListByParameter(final PagingQueryJobLogDTO pagingQuery) {
        // 查询
        Page<JobLogVO> pagingQueryList = this.jobLogService.pagingQueryListByParameter(pagingQuery);
        return pagingQueryList;
    }

}
