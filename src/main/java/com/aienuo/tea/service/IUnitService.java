package com.aienuo.tea.service;

import com.aienuo.tea.model.po.Unit;
import com.aienuo.tea.model.vo.UnitTreeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 组织机构 服务类
 */
public interface IUnitService extends IService<Unit> {

    /**
     * 查找 组织机构 树
     *
     * @return List<UnitTreeVO> - 组织机构
     */
    List<UnitTreeVO> queryUnitTreeList();

    /**
     * 查找指定 组织机构 下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Unit> - 组织机构
     */
    List<Unit> unitTree(final String id);

    /**
     * 查找指定 组织机构 及下属所有 下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Unit> - 组织机构
     */
    List<Unit> queryAllUnitList(final String id);

    /**
     * 查找指定 组织机构 的所有下级组织机构 以及 下级组织机构的 所有下级组织机构
     *
     * @param id - 组织机构标识
     * @return List<Unit> - 组织机构
     */
    List<Unit> queryChildAndAllUnitList(final String id);

}
