package com.aienuo.tea.model.converter;

import com.aienuo.tea.model.dto.PermissionAddDTO;
import com.aienuo.tea.model.dto.PermissionUpdateDTO;
import com.aienuo.tea.model.po.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 菜单权限转换类
 */
@Mapper
public interface PermissionConverter {

    /**
     * 系统菜单权限 实例
     */
    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 菜单权限添加对象
     * @return Permission - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "delFlag", constant = "0"),
    })
    Permission getAddEntity(final PermissionAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update     - 菜单权限更新对象
     * @param permission - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true, expression = "java(null)"),
            @Mapping(target = "updateTime", ignore = true, expression = "java(null)"),
            @Mapping(target = "delFlag", ignore = true),
            @Mapping(target = "children", ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Permission permission, final PermissionUpdateDTO update);

}
