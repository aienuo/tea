package com.aienuo.tea.mapper;

import com.aienuo.tea.model.po.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关系 Mapper 接口
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 查询权限标识
     *
     * @param roleId - 角色表示
     * @return List<String> - 权限标识
     */
    List<String> queryPermissionIdListByRoleId(@Param("roleId") final String roleId);

}
