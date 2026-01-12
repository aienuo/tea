package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.PagingQueryRoleDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RolePageVO;
import com.aienuo.tea.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色 服务类
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<RolePageVO> - 返回值
     */
    Page<RolePageVO> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleVO - 角色信息
     */
    RoleVO queryById(final String id);

    /**
     * 角色下拉列表
     *
     * @return OptionVO - 角色下拉
     */
    List<OptionVO> queryRoleList();

}
