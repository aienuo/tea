package com.aienuo.tea.mapper;

import com.aienuo.tea.model.po.Unit;
import com.aienuo.tea.model.vo.UnitTreeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 组织机构 Mapper 接口
 */
public interface UnitMapper extends BaseMapper<Unit> {

    /**
     * 树查询
     *
     * @return List<UnitTreeVO> - 组织机构
     */
    List<UnitTreeVO> queryList();

}
