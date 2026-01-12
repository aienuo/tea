package com.aienuo.tea.mapper;

import com.aienuo.tea.model.po.Permission;
import com.aienuo.tea.model.vo.ButtonVO;
import com.aienuo.tea.model.vo.MenuTreeVO;
import com.aienuo.tea.model.vo.PermissionTreeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限 Mapper 接口
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 树查询
     *
     * @return List<PermissionTreeVO> - 菜单权限
     */
    List<PermissionTreeVO> queryList();

    /**
     * 查询菜单权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return MenuTreeVO - 菜单
     */
    List<MenuTreeVO> queryMenuListByParameter(@Param("permissionIdList") final List<String> permissionIdList);

    /**
     * 查询按钮权限
     *
     * @param permissionIdList - 菜单权限标识
     * @return ButtonVO - 按钮
     */
    List<ButtonVO> queryButtonListByParameter(@Param("permissionIdList") final List<String> permissionIdList);

}
