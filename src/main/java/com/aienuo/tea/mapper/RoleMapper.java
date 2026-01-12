package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.PagingQueryRoleDTO;
import com.aienuo.tea.model.po.Role;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.model.vo.RolePageVO;
import com.aienuo.tea.model.vo.RoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper 接口
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery     - 分页查询对象
     * @return Page<RolePageVO> - 返回值
     */
    Page<RolePageVO> pagingQueryListByParameter(@Param("pg") final Page<RolePageVO> pagingQueryList, @Param("param") final PagingQueryRoleDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleVO - 角色信息
     */
    RoleVO queryById(@Param("id") final String id);

    /**
     * 角色下拉列表
     *
     * @return OptionVO - 角色下拉
     */
    List<OptionVO> list();

}
