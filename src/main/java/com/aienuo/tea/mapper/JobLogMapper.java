package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.PagingQueryJobLogDTO;
import com.aienuo.tea.model.po.JobLog;
import com.aienuo.tea.model.vo.JobLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务执行日志 Mapper 接口
 */
public interface JobLogMapper extends BaseMapper<JobLog> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery     - 分页查询对象
     * @return Page<JobLogVO> - 返回值
     */
    Page<JobLogVO> pagingQueryListByParameter(@Param("pg") final Page<JobLogVO> pagingQueryList, @Param("param") final PagingQueryJobLogDTO pagingQuery);

}