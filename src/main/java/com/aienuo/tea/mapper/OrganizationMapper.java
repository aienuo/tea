package com.aienuo.tea.mapper;

import com.aienuo.tea.model.po.Organization;
import com.aienuo.tea.model.vo.OrganizationTreeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 组织机构 Mapper 接口
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 树查询
     *
     * @return List<OrganizationTreeVO> - 组织机构
     */
    List<OrganizationTreeVO> queryList();

}
