package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.LogCleanDTO;
import com.aienuo.tea.model.dto.PagingQueryLogDTO;
import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.model.vo.LogVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统日志 服务类
 */
public interface ILogService extends IService<Log> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < LogVO>> - 返回值
     */
    Page<LogVO> pagingQueryListByParameter(final PagingQueryLogDTO pagingQuery);

    /**
     * 日志清除
     *
     * @param param - 参数
     * @return Long - 数量
     */
    Long clean(final LogCleanDTO param);

}
