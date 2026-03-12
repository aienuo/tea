package com.aienuo.tea.model.converter;

import com.aienuo.tea.model.dto.OrganizationAddDTO;
import com.aienuo.tea.model.dto.OrganizationUpdateDTO;
import com.aienuo.tea.model.po.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 组织机构转换类
 */
@Mapper
public interface OrganizationConverter {

    /**
     * 系统组织机构 实例
     */
    OrganizationConverter INSTANCE = Mappers.getMapper(OrganizationConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 组织机构添加对象
     * @return Organization - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "children", ignore = true),
    })
    Organization getAddEntity(final OrganizationAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update       - 组织机构更新对象
     * @param organization - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true, expression = "java(null)"),
            @Mapping(target = "updateTime", ignore = true, expression = "java(null)"),
            @Mapping(target = "children", ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Organization organization, final OrganizationUpdateDTO update);

}
