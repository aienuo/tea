package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.JobMapper;
import com.aienuo.tea.model.dto.PagingQueryJobDTO;
import com.aienuo.tea.model.po.Job;
import com.aienuo.tea.model.vo.JobVO;
import com.aienuo.tea.service.IJobService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < JobVO>> - 返回值
     */
    @Override
    public Page<JobVO> pagingQueryListByParameter(final PagingQueryJobDTO pagingQuery) {
        // 1、页码、页长
        Page<JobVO> pagingQueryList = new Page<>(pagingQuery.getCurrent(), pagingQuery.getSize());
        // 2、排序字段
        if (CollectionUtils.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

}
