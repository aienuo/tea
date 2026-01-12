package com.aienuo.tea.business;

import com.aienuo.tea.common.base.BaseBusiness;
import com.aienuo.tea.common.enums.ArgumentResponseEnum;
import com.aienuo.tea.model.converter.UserConverter;
import com.aienuo.tea.model.dto.PagingQueryUserDTO;
import com.aienuo.tea.model.dto.ResetPasswordDTO;
import com.aienuo.tea.model.dto.UserAddDTO;
import com.aienuo.tea.model.dto.UserUpdateDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.po.Unit;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.po.UserRole;
import com.aienuo.tea.model.vo.UserPageVO;
import com.aienuo.tea.service.IRoleService;
import com.aienuo.tea.service.IUnitService;
import com.aienuo.tea.service.IUserRoleService;
import com.aienuo.tea.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserBusiness extends BaseBusiness {

    /**
     * 系统用户
     */
    private final IUserService userService;

    /**
     * 系统角色
     */
    private final IRoleService roleService;

    /**
     * 用户角色关联
     */
    private final IUserRoleService userRoleService;

    /**
     * 组织机构 服务类
     */
    private final IUnitService unitService;

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<UserPageVO> - 用户信息
     */
    public Page<UserPageVO> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery) {
        // 查询 当前单位下所有单位信息 （包含自己）
        List<Unit> unitList = this.unitService.queryAllUnitList(pagingQuery.getUnitId());
        List<String> unitIdList = unitList.stream().map(Unit::getId).toList();
        // 数据分析单位列表
        pagingQuery.setUnitIdList(unitIdList);
        // 查询数据
        Page<UserPageVO> pagingQueryList = this.userService.pagingQueryListByParameter(pagingQuery);
        if (CollectionUtils.isNotEmpty(pagingQueryList.getRecords())) {
            // 用户标识
            List<String> userIdList = pagingQueryList.getRecords().stream().map(UserPageVO::getId).filter(StringUtils::isNotEmpty).distinct().toList();
            // 角色信息
            List<Role> roleQueryList = this.roleService.list();
            // 角色信息名称 Map
            Map<String, String> roleMap = roleQueryList.stream().collect(Collectors.toMap(Role::getId, Role::getName));
            // 用户角色关系 集合
            List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().in(UserRole::getUserId, userIdList));
            // 角色集合
            Map<String, List<UserRole>> userRoleListMap = userRoleList.stream().collect(Collectors.groupingBy(UserRole::getUserId));
            // 单位名称 Map
            Map<String, String> unitMap = unitList.stream().collect(Collectors.toMap(Unit::getId, Unit::getName));
            // 建构数据
            pagingQueryList.getRecords().forEach(
                    data -> {
                        // 用户标识
                        String userId = data.getId();
                        if (userRoleListMap.containsKey(userId)) {
                            List<UserRole> userHave = userRoleListMap.get(userId);
                            // 角色信息
                            List<UserPageVO.Role> roleList = new ArrayList<>(userHave.size());
                            // 组织数据
                            userHave.forEach(
                                    userRole -> {
                                        // 角色
                                        UserPageVO.Role role = new UserPageVO.Role();
                                        role.setId(userRole.getRoleId());
                                        if (roleMap.containsKey(role.getId())) {
                                            // 角色名称
                                            role.setName(roleMap.get(role.getId()));
                                        }
                                        roleList.add(role);
                                    }
                            );
                            data.setRoleList(roleList);
                        }
                        // 单位信息
                        if (unitMap.containsKey(data.getUnitId())) {
                            data.setUnitName(unitMap.get(data.getUnitId()));
                        }
                    }
            );
        }
        return pagingQueryList;
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return Boolean - 成功标识
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(final UserAddDTO add) {
        // 1、验证 用户帐号\身份证号码 是否存在重复
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, add.getUsername()).or().eq(User::getIdentityNumber, add.getIdentityNumber()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(user, "用户", "帐号&身份证号码存在重复");
        // 2、将添加对象生成新的数据库对象
        user = UserConverter.INSTANCE.getAddEntity(add);
        // 3、创建新用户
        boolean save = this.userService.insertOrUpdate(user);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "请确认信息准确无误后重新添加");
        // 4、创建用户角色关联
        List<String> roleList = add.getRoleList();
        if (CollectionUtils.isNotEmpty(roleList)) {
            List<UserRole> userRoleList = UserConverter.INSTANCE.getUserRoleEntity(user.getId(), roleList);
            save = this.userRoleService.saveBatch(userRoleList);
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "用户与角色关联失败");
        }
        return save;
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return Boolean - 成功标识
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(final UserUpdateDTO update) {
        // 1、更新校验
        User user = this.userService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(user, "用户", "用户信息不存在");
        // 2、将更新对象赋值到数据库对象
        UserConverter.INSTANCE.getUpdateEntity(user, update);
        // 3、清除原来的 用户角色关联
        this.userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
        // 4、更新用户
        boolean save = this.userService.insertOrUpdate(user);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "请确认信息准确无误后重新更新");
        // 5、更新用户角色关系
        List<String> roleList = update.getRoleList();
        if (CollectionUtils.isNotEmpty(roleList)) {
            List<UserRole> userRoleList = UserConverter.INSTANCE.getUserRoleEntity(user.getId(), roleList);
            save = this.userRoleService.saveBatch(userRoleList);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "用户与角色关联失败");
        }
        return save;
    }

    /**
     * 重置密码
     *
     * @param resetPassword - 重置密码参数
     * @return Boolean - 成功标识
     */
    public Boolean resetPassword(final ResetPasswordDTO resetPassword) {
        // 1、验证用户存在
        User user = this.userService.getById(resetPassword.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(user, "用户", "请确认信息准确无误后重新重置密码");
        // 2、数据库存储密码
        user.setPassword(resetPassword.getPassword());
        // 3、更新用户
        boolean update = this.userService.insertOrUpdate(user);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(update, "用户", "请确认信息准确无误后重新重置密码");
        return update;
    }

    /**
     * 移除
     *
     * @param idList - 用户标识
     * @return Boolean - 成功标识
     */
    public Boolean delete(final List<String> idList) {
        boolean delete = this.userRoleService.remove(Wrappers.<UserRole>lambdaQuery().in(UserRole::getUserId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "用户角色关系", "请确认信息准确无误后重新删除");
        delete = this.userService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "菜单权限", "请确认信息准确无误后重新删除");
        return delete;
    }

    /**
     * 冻结/解冻
     *
     * @param idList - 用户标识
     * @return Boolean - 成功标识
     */
    public Boolean update(final List<String> idList) {
        List<User> userList = this.userService.listByIds(idList);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotEmpty(userList, "用户", "请确认信息准确无误后重新操作");
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(idList.size() == userList.size(), "用户", "请确认信息准确无误后重新操作");
        userList.forEach(
                // 删除状态（0-正常，1-已删除）
                user -> user.setDelFlag(Math.abs(user.getDelFlag() - 1))
        );
        boolean update = this.userService.updateBatchById(userList);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(update, "用户", "请确认信息准确无误后重新操作");
        return update;
    }

}

