package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.OrganizationMapper;
import com.aienuo.tea.model.po.Organization;
import com.aienuo.tea.model.vo.OrganizationTreeVO;
import com.aienuo.tea.service.IOrganizationService;
import com.aienuo.tea.utils.BuildingTreeData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 组织机构 服务实现类
 */

@Slf4j
@DS("master")
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    /**
     * 查找 组织机构 树
     *
     * @return List<OrganizationTreeVO> - 组织机构
     */
    @Override
    @DS("master")
    public List<OrganizationTreeVO> queryOrganizationTreeList() {
        // 1、查询数据
        List<OrganizationTreeVO> list = this.baseMapper.queryList();
        if (CollectionUtils.isNotEmpty(list)) {
            // 2、构建树形数据
            return new BuildingTreeData().buildingTreeData(list);
        }
        return list;
    }

    /**
     * 查找指定 组织机构 下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "organization", key = "'tree_by_parent_id_' + #id")
    public List<Organization> organizationTree(final String id) {
        if (StringUtils.isNotEmpty(id) && !StringPool.ZERO.equals(id)) {
            // 查找指定 组织机构 下属所有 下级组织机构
            return new BuildingTreeData().buildingTreeData(id, this.list());
        }
        // 所有组织机构数据
        return new BuildingTreeData().buildingTreeData(this.list());
    }

    /**
     * 查找指定 组织机构 及下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "organization", key = "'list_by_id_' + #id")
    public List<Organization> queryAllOrganizationList(final String id) {
        // 所有组织机构数据
        List<Organization> list = this.list();
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotEmpty(id)) {
            List<Organization> dataList = new ArrayList<>();
            new BuildingTreeData<Organization>().findAllData(Boolean.TRUE, dataList, id, list);
            return dataList;
        }
        return list;
    }

    /**
     * 查找指定 组织机构 的所有下级组织机构 以及 下级组织机构的 所有下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Organization> - 组织机构
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "organization", key = "'child_list_by_parent_id_' + #id")
    public List<Organization> queryChildAndAllOrganizationList(final String id) {
        // 查询数据
        List<Organization> organizationList = this.list();
        if (CollectionUtils.isNotEmpty(organizationList) && StringUtils.isNotEmpty(id)) {
            List<Organization> dataList = new ArrayList<>();
            // 构建数据
            organizationList.stream()
                    // 过滤出 顶级节点
                    .filter(item -> StringUtils.isNotEmpty(item.getParentId()) && id.equals(item.getParentId()))
                    // 排序
                    .sorted(Comparator.comparingDouble(Organization::getSortNo))
                    // 遍历顶级节点处理数据
                    .forEach(
                            parent -> {
                                List<Organization> childList = new ArrayList<>();
                                // 查找指定节点及下所有子节点
                                new BuildingTreeData<Organization>().findAllData(Boolean.FALSE, childList, parent.getId(), organizationList);
                                parent.setChildren(childList);
                                dataList.add(parent);
                            }
                    );
            return dataList;
        }
        return organizationList;
    }

}
