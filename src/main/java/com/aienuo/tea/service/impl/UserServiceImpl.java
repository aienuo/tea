package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.UserMapper;
import com.aienuo.tea.model.dto.PagingQueryUserDTO;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.vo.UserPageVO;
import com.aienuo.tea.service.IUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 系统用户 服务实现类
 */

@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    // 清理缓存 @CacheEvict(cacheNames = "user", allEntries = true)

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<UserPageVO> - 用户信息
     */
    @Override
    public Page<UserPageVO> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery) {
        // 1、页码、页长
        Page<UserPageVO> pagingQueryList = new Page<>(pagingQuery.getCurrent(), pagingQuery.getSize());
        // 2、排序字段
        if (CollectionUtils.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 加载用户信息
     *
     * @param username - 用户名
     * @return UserDetails - 用户信息
     * @throws UsernameNotFoundException -
     */
    @Override
    @DS("master")
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.queryUserByUserName(username);
    }


    /**
     * 添加
     *
     * @param user - 用户信息
     * @return Boolean - 成功标识
     */
    @Override
    @DS("master")
    @CacheEvict(cacheNames = "user", key = "#user.username", allEntries = true)
    public Boolean insertOrUpdate(final User user) {
        return this.saveOrUpdate(user);
    }

    /**
     * 查询
     *
     * @param username - 用户名
     * @return User - 用户信息
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "user", key = "#username")
    public User queryUserByUserName(final String username) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username), Boolean.FALSE);
    }

}
