package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.PagingQueryJobLogDTO;
import com.aienuo.tea.model.po.JobLog;
import com.aienuo.tea.model.vo.JobLogVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 定时任务执行日志 服务类
 */
public interface IJobLogService extends IService<JobLog> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < JobLogVO>> - 返回值
     */
    Page<JobLogVO> pagingQueryListByParameter(final PagingQueryJobLogDTO pagingQuery);

}
