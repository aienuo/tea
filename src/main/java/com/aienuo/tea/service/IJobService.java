package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.PagingQueryJobDTO;
import com.aienuo.tea.model.po.Job;
import com.aienuo.tea.model.vo.JobVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 定时任务 服务类
 */
public interface IJobService extends IService<Job> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < JobVO>> - 返回值
     */
    Page<JobVO> pagingQueryListByParameter(final PagingQueryJobDTO pagingQuery);

}
