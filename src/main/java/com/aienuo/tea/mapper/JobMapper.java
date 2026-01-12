package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.PagingQueryJobDTO;
import com.aienuo.tea.model.po.Job;
import com.aienuo.tea.model.vo.JobVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务 Mapper 接口
 */
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery     - 分页查询对象
     * @return Page<JobVO> - 返回值
     */
    Page<JobVO> pagingQueryListByParameter(@Param("pg") final Page<JobVO> pagingQueryList, @Param("param") final PagingQueryJobDTO pagingQuery);

}