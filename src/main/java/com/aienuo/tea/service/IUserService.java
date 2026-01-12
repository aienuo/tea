package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.PagingQueryUserDTO;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.model.vo.UserPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统用户 服务类
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<UserPageVO> - 用户信息
     */
    Page<UserPageVO> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery);

    /**
     * 添加用户信息
     *
     * @param user - 用户信息
     * @return Boolean - 成功标识
     */
    Boolean insertOrUpdate(final User user);

    /**
     * 依据用户名查询用户信息
     *
     * @param username - 用户名
     * @return User - 用户信息
     */
    User queryUserByUserName(final String username);

}
