package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.RolePermissionMapper;
import com.aienuo.tea.model.po.RolePermission;
import com.aienuo.tea.service.IRolePermissionService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色权限关系 服务实现类
 */
@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
