package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.UserRoleMapper;
import com.aienuo.tea.model.po.UserRole;
import com.aienuo.tea.service.IUserRoleService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
