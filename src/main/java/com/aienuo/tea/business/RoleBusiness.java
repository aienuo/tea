package com.aienuo.tea.business;

import com.aienuo.tea.common.base.BaseBusiness;
import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.model.converter.RoleConverter;
import com.aienuo.tea.model.dto.PagingQueryRoleDTO;
import com.aienuo.tea.model.dto.RoleAddDTO;
import com.aienuo.tea.model.dto.RoleUpdateDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.po.RolePermission;
import com.aienuo.tea.model.po.UserRole;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RolePageVO;
import com.aienuo.tea.model.vo.RoleVO;
import com.aienuo.tea.service.IRolePermissionService;
import com.aienuo.tea.service.IRoleService;
import com.aienuo.tea.service.IUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统角色
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleBusiness extends BaseBusiness {

    /**
     * 系统角色
     */
    private final IRoleService roleService;

    /**
     * 用户角色关系
     */
    private final IUserRoleService userRoleService;

    /**
     * 角色菜单权限关系
     */
    private final IRolePermissionService rolePermissionService;

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<RolePageVO> - 返回值
     */
    public Page<RolePageVO> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery) {
        // 分页查询
        Page<RolePageVO> pagingQueryList = this.roleService.pagingQueryListByParameter(pagingQuery);
        return pagingQueryList;
    }

    /**
     * 添加
     *
     * @param create - 添加参数
     * @return Boolean - 成功标识
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(final RoleAddDTO create) {
        // 1、验证 角色名称 是否存在重复
        Role role = this.roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, create.getName()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(role, "角色", "角色名称存在重复");
        // 2、将添加对象生成新的数据库对象
        role = RoleConverter.INSTANCE.getAddEntity(create);
        // 3、创建新角色
        boolean save = this.roleService.save(role);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "请确认信息准确无误后重新添加");
        // 4、创建角色菜单权限关系
        List<String> permissionList = create.getPermissionList();
        if (CollectionUtils.isNotEmpty(permissionList)) {
            List<RolePermission> rolePermissionList = RoleConverter.INSTANCE.getRolePermissionEntity(role.getId(), permissionList);
            save = this.rolePermissionService.saveBatch(rolePermissionList);
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "角色与菜单权限关联失败");
        }
        return save;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleVO - 角色
     */
    public RoleVO queryById(final String id) {
        // 查询信息
        RoleVO role = this.roleService.queryById(id);
        return role;
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return Boolean - 成功标识
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(final RoleUpdateDTO update) {
        // 1、更新校验
        Role role = this.roleService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(role, "角色", "角色信息不存在");
        if (StringUtils.isNotEmpty(update.getName()) && !role.getName().equals(update.getName())) {
            // 验证 角色名称 是否存在重复
            Role roleByRoleName = this.roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, update.getName()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(roleByRoleName, "角色", "角色名称存在重复");
        }
        // 2、将更新对象赋值到数据库对象
        RoleConverter.INSTANCE.getUpdateEntity(role, update);
        // 3、更新角色
        boolean save = this.roleService.updateById(role);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "请确认信息准确无误后重新更新");
        // 4、清除角色菜单权限关系
        this.rolePermissionService.remove(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRole, role.getId()));
        // 5、更新角色菜单权限关系
        List<String> permissionList = update.getPermissionList();
        if (CollectionUtils.isNotEmpty(permissionList)) {
            List<RolePermission> rolePermissionList = RoleConverter.INSTANCE.getRolePermissionEntity(role.getId(), permissionList);
            save = this.rolePermissionService.saveBatch(rolePermissionList);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "更新角色菜单权限关系失败");
        }
        return save;
    }

    /**
     * 删除
     *
     * @param idList - 角色标识
     * @return Boolean - 成功标识
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(final List<String> idList) {
        long number = this.userRoleService.count(Wrappers.<UserRole>lambdaQuery().in(UserRole::getRoleId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(number == 0, "角色", "角色已被分配给用户");
        long hava = this.rolePermissionService.count(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRole, idList));
        if (hava > 0) {
            boolean delete = this.rolePermissionService.remove(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRole, idList));
            ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "角色", "角色与菜单权限关联关系解除失败");
        }
        boolean delete = this.roleService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "角色", "请确认信息准确无误后重新删除");
        return delete;
    }

    /**
     * 角色下拉列表
     *
     * @return OptionVO - 角色下拉
     */
    public List<OptionVO> list() {
        return this.roleService.queryRoleList();
    }

}

