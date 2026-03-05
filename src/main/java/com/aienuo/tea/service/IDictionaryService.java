package com.aienuo.tea.service;

import com.aienuo.tea.model.dto.DictionaryQueryDTO;
import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.po.Dictionary;
import com.aienuo.tea.model.vo.OptionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 数据字典 服务类
 */
public interface IDictionaryService extends IService<Dictionary> {

    /**
     * 数据字典 查询树形结构
     *
     * @param query - 查询参数
     * @return List<Dictionary> - 数据字典
     */
    List<Dictionary> queryDictionaryList(final DictionaryQueryDTO query);

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
     * @return List<Dictionary> - 数据字典
     */
    List<Dictionary> queryDictionaryList(final String type);

    /**
     * 数据字典 查询顶级 以及 所有的子级列表
     *
     * @param type - 类型名称
     * @return List<Dictionary> - 数据字典
     */
    List<Dictionary> queryDictionaryTopAndAllChildList(final String type);

    /**
     * 数据字典 查询指定 以及 所有的子级列表
     *
     * @param type  - 类型名称
     * @param value - 字典数值
     * @return List<Dictionary> - 数据字典
     */
    List<Dictionary> queryDictionaryListByValue(final String type, final String value);

    /**
     * 数据字典 查询所有，不分层级
     *
     * @param type - 类型名称
     * @return List<Dictionary> - 数据字典
     */
    List<Dictionary> queryAllDictionaryList(final String type);

}
