package com.aienuo.tea.mapper;

import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.po.Dict;
import com.aienuo.tea.model.vo.OptionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典 Mapper 接口
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 查询表信息
     *
     * @param query - 查询参数
     * @return OptionVO - 表信息
     */
    List<OptionVO> queryTableItemListForMySQL(@Param("param")final OptionDTO query);

    /**
     * 查询表信息
     *
     * @param query - 查询参数
     * @return OptionVO - 表信息
     */
    List<OptionVO> queryTableItemListForDM(@Param("param")final OptionDTO query);

}
