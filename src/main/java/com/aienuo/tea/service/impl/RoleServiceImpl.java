package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.RoleMapper;
import com.aienuo.tea.model.dto.PagingQueryRoleDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RolePageVO;
import com.aienuo.tea.model.vo.RoleVO;
import com.aienuo.tea.service.IRoleService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<RolePageVO> - 返回值
     */
    @Override
    @DS("master")
    public Page<RolePageVO> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery) {
        // 1、页码、页长
        Page<RolePageVO> pagingQueryList = new Page<>(pagingQuery.getCurrent(), pagingQuery.getSize());
        // 2、排序字段
        if (CollectionUtils.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleVO - 角色信息
     */
    @Override
    @DS("master")
    public RoleVO queryById(final String id) {
        return this.baseMapper.queryById(id);
    }

    /**
     * 角色下拉列表
     *
     * @return OptionVO - 角色下拉
     */
    @Override
    @DS("master")
    public List<OptionVO> queryRoleList() {
        return this.baseMapper.list();
    }

}
