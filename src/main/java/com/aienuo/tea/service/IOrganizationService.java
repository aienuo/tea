package com.aienuo.tea.service;

import com.aienuo.tea.model.po.Organization;
import com.aienuo.tea.model.vo.OrganizationTreeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 组织机构 服务类
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 查找 组织机构 树
     *
     * @return List<OrganizationTreeVO> - 组织机构
     */
    List<OrganizationTreeVO> queryOrganizationTreeList();

    /**
     * 查找指定 组织机构 下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    List<Organization> organizationTree(final String id);

    /**
     * 查找指定 组织机构 及下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    List<Organization> queryAllOrganizationList(final String id);

    /**
     * 查找指定 组织机构 的所有下级组织机构 以及 下级组织机构的 所有下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    List<Organization> queryChildAndAllOrganizationList(final String id);

}
