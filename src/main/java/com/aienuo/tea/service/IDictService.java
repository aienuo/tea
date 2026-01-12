package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.DictQueryDTO;
import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.po.Dict;
import com.aienuo.tea.model.vo.OptionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 数据字典 服务类
 */
public interface IDictService extends IService<Dict> {

    /**
     * 数据字典 查询树形结构
     *
     * @param query - 查询参数
     * @return List<Dict> - 数据字典
     */
    List<Dict> queryDictList(final DictQueryDTO query);

    /**
     * 查询表信息
     *
     * @param query - 查询参数
     * @return OptionVO - 表信息
     */
    List<OptionVO> queryTableItemList(final OptionDTO query);

    /**
     * 数据字典 查询树形结构
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    List<Dict> queryDictList(final String type);

    /**
     * 数据字典 查询顶级 以及 所有的子级列表
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    List<Dict> queryDictTopAndAllChildList(final String type);

    /**
     * 数据字典 查询指定 以及 所有的子级列表
     *
     * @param type  - 类型名称
     * @param value - 字典数值
     * @return List<Dict> - 数据字典
     */
    List<Dict> queryDictListByValue(final String type, final String value);

    /**
     * 数据字典 查询所有，不分层级
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    List<Dict> queryAllDictList(final String type);

}
