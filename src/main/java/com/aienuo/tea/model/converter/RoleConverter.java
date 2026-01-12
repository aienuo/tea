package com.aienuo.tea.model.converter;

import com.aienuo.tea.model.dto.RoleAddDTO;
import com.aienuo.tea.model.dto.RoleUpdateDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.po.RolePermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色 转换类
 */
@Mapper
public interface RoleConverter {

    /**
     * 系统角色 实例
     */
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 角色添加对象
     * @return Role
     */
    @Mappings({
            @Mapping(target = "id", expression = "java(com.baomidou.mybatisplus.core.toolkit.IdWorker.getIdStr())"),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
    })
    Role getAddEntity(final RoleAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 角色更新对象
     * @param role   - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Role role, final RoleUpdateDTO update);

    /**
     * 角色菜单权限关系
     *
     * @param role     - 角色标识
     * @param menuList - 菜单权限标识
     * @return List<RolePermission> - 角色菜单权限关系
     */
    default List<RolePermission> getRolePermissionEntity(final String role, final List<String> menuList) {
        List<RolePermission> rolePermissionList = new ArrayList<>();
        menuList.forEach(
                menu -> rolePermissionList.add(new RolePermission().setRole(role).setPermission(menu))
        );
        return rolePermissionList;
    }

}
