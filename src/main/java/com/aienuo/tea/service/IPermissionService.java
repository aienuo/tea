package com.aienuo.tea.service;

import com.aienuo.tea.model.po.Permission;
import com.aienuo.tea.model.vo.ButtonVO;
import com.aienuo.tea.model.vo.MenuTreeVO;
import com.aienuo.tea.model.vo.PermissionTreeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单权限 服务类
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 树查询
     *
     * @return List<PermissionTreeVO> - 菜单权限
     */
    List<PermissionTreeVO> queryPermissionTreeList();

    /**
     * 查询菜单权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return MenuTreeVO - 菜单
     */
    List<MenuTreeVO> queryMenuTreeListByIdList(final List<String> permissionIdList);

    /**
     * 查询按钮权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return ButtonVO - 按钮
     */
    List<ButtonVO> queryButtonListByIdList(final List<String> permissionIdList);

}
