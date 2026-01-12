package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.LogCleanDTO;
import com.aienuo.tea.model.dto.PagingQueryLogDTO;
import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.model.vo.LogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 系统日志 Mapper 接口
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery     - 分页查询对象
     * @return Page<JobVO> - 返回值
     */
    Page<LogVO> pagingQueryListByParameter(@Param("pg") final Page<LogVO> pagingQueryList, @Param("param") final PagingQueryLogDTO pagingQuery);

    /**
     * 日志清除
     *
     * @param param - 参数
     * @return Long - 数量
     */
    Long clean(@Param("param") final LogCleanDTO param);

}
