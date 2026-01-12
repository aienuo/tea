package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.LogMapper;
import com.aienuo.tea.model.dto.LogCleanDTO;
import com.aienuo.tea.model.dto.PagingQueryLogDTO;
import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.model.vo.LogVO;
import com.aienuo.tea.service.ILogService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统日志 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < LogVO>> - 返回值
     */
    @Override
    public Page<LogVO> pagingQueryListByParameter(final PagingQueryLogDTO pagingQuery) {
        // 1、页码、页长
        Page<LogVO> pagingQueryList = new Page<>(pagingQuery.getCurrent(), pagingQuery.getSize());
        // 2、排序字段
        if (CollectionUtils.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 日志清除
     *
     * @param param - 参数
     * @return Long - 数量
     */
    public Long clean(final LogCleanDTO param) {
        return this.baseMapper.clean(param);
    }

}
