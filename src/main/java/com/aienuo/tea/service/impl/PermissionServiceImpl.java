package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.PermissionMapper;
import com.aienuo.tea.model.po.Permission;
import com.aienuo.tea.model.vo.ButtonVO;
import com.aienuo.tea.model.vo.MenuTreeVO;
import com.aienuo.tea.model.vo.PermissionTreeVO;
import com.aienuo.tea.service.IPermissionService;
import com.aienuo.tea.utils.BuildingTreeData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    /**
     * 树查询
     *
     * @return List<PermissionTreeVO> - 菜单权限
     */
    @Override
    @DS("master")
    public List<PermissionTreeVO> queryPermissionTreeList() {
        // 1、查询数据
        List<PermissionTreeVO> list = this.baseMapper.queryList();
        if (CollectionUtils.isNotEmpty(list)) {
            // 2、构建树形数据
            return new BuildingTreeData().buildingTreeData(list);
        }
        return list;
    }

    /**
     * 查询菜单权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return MenuTreeVO - 菜单
     */
    @Override
    public List<MenuTreeVO> queryMenuTreeListByIdList(final List<String> permissionIdList) {
        // 1、查询数据
        List<MenuTreeVO> list = this.baseMapper.queryMenuListByParameter(permissionIdList);
        if (CollectionUtils.isNotEmpty(list)) {
            // 构建树形结构数据
            return new BuildingTreeData<MenuTreeVO>().buildingTreeData(list);
        }
        return List.of();
    }

    /**
     * 查询按钮权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return ButtonVO - 按钮
     */
    @Override
    public List<ButtonVO> queryButtonListByIdList(final List<String> permissionIdList) {
        // 1、查询数据
        List<ButtonVO> list = this.baseMapper.queryButtonListByParameter(permissionIdList);
        return list;
    }

}
