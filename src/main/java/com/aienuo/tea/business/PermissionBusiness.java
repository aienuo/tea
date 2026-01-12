package com.aienuo.tea.business;

import com.aienuo.tea.common.base.BaseBusiness;
import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.model.converter.PermissionConverter;
import com.aienuo.tea.model.dto.PermissionAddDTO;
import com.aienuo.tea.model.dto.PermissionUpdateDTO;
import com.aienuo.tea.model.po.Permission;
import com.aienuo.tea.model.po.RolePermission;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.po.UserRole;
import com.aienuo.tea.model.vo.ButtonVO;
import com.aienuo.tea.model.vo.MenuTreeVO;
import com.aienuo.tea.model.vo.PermissionTreeVO;
import com.aienuo.tea.service.IPermissionService;
import com.aienuo.tea.service.IRolePermissionService;
import com.aienuo.tea.service.IUserRoleService;
import com.aienuo.tea.utils.BuildingTreeData;
import com.aienuo.tea.utils.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionBusiness extends BaseBusiness {

    /**
     * 菜单权限
     */
    private final IPermissionService permissionService;

    /**
     * 角色菜单权限关系
     */
    private final IRolePermissionService rolePermissionService;

    /**
     * 用户角色关系
     */
    private final IUserRoleService userRoleService;

    /**
     * 树查询
     *
     * @return List<PermissionTreeVO> - 菜单权限
     */
    public List<PermissionTreeVO> treeList() {
        return this.permissionService.queryPermissionTreeList();
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return Boolean - 成功标识
     */
    public Boolean create(final PermissionAddDTO add) {
        if (StringUtils.isNotEmpty(add.getParentId())) {
            // 1、验证父级菜单权限是否存在
            Permission parent = this.permissionService.getById(add.getParentId());
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertNotNull(parent, "菜单权限", "父级菜单权限信息不存在");
        }
        // 2、验证 菜单权限名称 是否存在重复
        Permission permission = this.permissionService.getOne(Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getName, add.getName()).eq(Permission::getType, add.getType())
                .eq(StringUtils.isNotEmpty(add.getParentId()), Permission::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(permission, "菜单权限", "菜单权限名称存在重复");
        // 3、 将添加对象生成新的数据库对象
        permission = PermissionConverter.INSTANCE.getAddEntity(add);
        // 4、创建新菜单权限
        boolean save = this.permissionService.save(permission);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "菜单权限", "请确认信息准确无误后重新添加");
        return save;
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return Boolean - 成功标识
     */
    public Boolean update(final PermissionUpdateDTO update) {
        // 1、更新校验
        if (StringUtils.isNotBlank(update.getParentId())) {
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsFalse(Objects.equals(update.getParentId(), update.getId()), "菜单权限", "请确认信息准确无误后重新更新");
            // 2、校验是否把自己的父节点改成了 自己 的子节点
            List<Permission> list = this.permissionService.list();
            if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(update.getId())) {
                List<Permission> dataList = new ArrayList<>();
                new BuildingTreeData<Permission>().findAllData(Boolean.TRUE, dataList, update.getId(), list);
                List<String> chilidIdList = dataList.stream().map(Permission::getId).filter(StringUtils::isNotBlank).distinct().toList();
                ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsFalse(chilidIdList.contains(update.getParentId()), "菜单权限", "请确认信息准确无误后重新更新");
            }
        }
        // 3、查询数据库数据
        Permission permission = this.permissionService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(permission, "菜单权限", "菜单权限信息不存在");
        if (StringUtils.isNotEmpty(update.getParentId()) && !update.getParentId().equals(permission.getParentId())) {
            // 4、验证父级菜单权限是否存在
            Permission parent = this.permissionService.getById(update.getParentId());
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(parent, "菜单权限", "父级菜单权限信息不存在");
        }
        if (StringUtils.isNotEmpty(update.getName()) && !permission.getName().equals(update.getName())) {
            // 5、验证 菜单权限名称 是否存在重复
            Permission permissionByName = this.permissionService.getOne(Wrappers.<Permission>lambdaQuery()
                            .eq(Permission::getName, update.getName()).eq(Permission::getType, update.getType())
                            .eq(StringUtils.isNotEmpty(update.getParentId()), Permission::getParentId, update.getParentId())
                    , Boolean.FALSE
            );
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(permissionByName, "菜单权限", "菜单权限名称存在重复");
        }
        // 6、将更新对象赋值到数据库对象
        PermissionConverter.INSTANCE.getUpdateEntity(permission, update);
        // 7、更新菜单权限
        boolean save = this.permissionService.updateById(permission);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "菜单权限", "请确认信息准确无误后重新更新");
        return save;
    }

    /**
     * 移除
     *
     * @param idList - 菜单权限标识
     * @return Boolean - 成功标识
     */
    public Boolean delete(final List<String> idList) {
        long count = this.permissionService.count(Wrappers.<Permission>lambdaQuery().in(Permission::getParentId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(count == 0, "菜单权限", "部分菜单权限下存在子节点");
        long rolePermissionCount = this.rolePermissionService.count(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getPermission, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(rolePermissionCount == 0, "菜单权限", "部分菜单权限已分配给角色使用");
        boolean delete = this.permissionService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "菜单权限", "请确认信息准确无误后重新删除");
        return delete;
    }

    /**
     * 查询系统用户的菜单权限
     *
     * @return List<MenuTreeVO> - 菜单权限
     */
    public List<MenuTreeVO> queryMenuTreeListByUserId() {
        User user = SecurityFrameworkUtils.getLoginUser();
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            // 用户角色关联
            List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
            if (CollectionUtils.isNotEmpty(userRoleList)) {
                // 角色标识
                List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                // 角色菜单权限关系
                List<RolePermission> rolePermissionList = this.rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRole, roleIdList));
                if (CollectionUtils.isNotEmpty(rolePermissionList)) {
                    List<String> permissionIdList = rolePermissionList.stream().map(RolePermission::getPermission).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                    // 查询数据
                    return this.permissionService.queryMenuTreeListByIdList(permissionIdList);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * 查询系统用户的按钮权限信息
     *
     * @return List<ButtonVO> - 按钮权限
     */
    public List<ButtonVO> queryButtonListByUserId() {
        User user = SecurityFrameworkUtils.getLoginUser();
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            // 用户角色关联
            List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
            if (CollectionUtils.isNotEmpty(userRoleList)) {
                // 角色标识
                List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                // 角色按钮权限关系
                List<RolePermission> rolePermissionList = this.rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRole, roleIdList));
                if (CollectionUtils.isNotEmpty(userRoleList)) {
                    List<String> permissionIdList = rolePermissionList.stream().map(RolePermission::getPermission).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                    return this.permissionService.queryButtonListByIdList(permissionIdList);
                }
            }
        }
        return new ArrayList<>();
    }

}

