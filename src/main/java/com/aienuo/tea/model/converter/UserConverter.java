package com.aienuo.tea.model.converter;

import com.aienuo.tea.model.dto.UserAddDTO;
import com.aienuo.tea.model.dto.UserUpdateDTO;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.po.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户 转换类
 */
@Mapper
public interface UserConverter {

    /**
     * 系统用户 实例
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 用户添加对象
     * @return User
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "delFlag", constant = "0"),
            @Mapping(target = "authorities", ignore = true),
    })
    User getAddEntity(final UserAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 用户更新对象
     * @param user   - 数据库对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creatorBy", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updaterBy", expression = "java(null)"),
            @Mapping(target = "updateTime", expression = "java(null)"),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "identityNumber", ignore = true),
            @Mapping(target = "delFlag", ignore = true),
    })
    void getUpdateEntity(@MappingTarget final User user, final UserUpdateDTO update);

    /**
     * 用户角色关联
     *
     * @param user     - 用户标识
     * @param roleIdList - 角色标识列表
     * @return List<UserRole> - 用户角色关系
     */
    default List<UserRole> getUserRoleEntity(final String user, final List<String> roleIdList) {
        List<UserRole> userRoleList = new ArrayList<>();
        roleIdList.forEach(
                role -> userRoleList.add(new UserRole().setUserId(user).setRoleId(role))
        );
        return userRoleList;
    }

}
