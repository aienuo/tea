package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.DictionaryMapper;
import com.aienuo.tea.model.dto.DictionaryQueryDTO;
import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.po.Dictionary;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.service.IDictionaryService;
import com.aienuo.tea.utils.BuildingTreeData;
import com.aienuo.tea.utils.RequestDataHelper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 数据字典 服务实现类
 */
@Service
@Slf4j
@DS("master")
@RequiredArgsConstructor
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    /**
     * 数据字典 查询树形结构
     *
     * @param query - 查询参数
     * @return List<Dictionary> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dictionary", keyGenerator = "redisKeyGenerator")
    public List<Dictionary> queryDictionaryList(final DictionaryQueryDTO query) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", query.getType());
            }});
            // 查询数据
            List<Dictionary> dictionaryItemList = this.list(Wrappers.<Dictionary>lambdaQuery()
                    .eq(StringUtils.isNotEmpty(query.getLabel()), Dictionary::getLabel, query.getLabel())
                    .eq(StringUtils.isNotEmpty(query.getValue()), Dictionary::getValue, query.getValue())
            );
            if (CollectionUtils.isNotEmpty(dictionaryItemList)) {
                if (StringUtils.isNotEmpty(query.getId())) {
                    List<Dictionary> dataList = new ArrayList<>();
                    // 构建数据
                    new BuildingTreeData<Dictionary>().findAllData(Boolean.FALSE, dataList, query.getId(), dictionaryItemList);
                    return dataList;
                } else {
                    return new BuildingTreeData<Dictionary>().buildingTreeData(dictionaryItemList);
                }
            }
            return dictionaryItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 查询表信息
     *
     * @param query - 查询参数
     * @return OptionVO - 表信息
     */
    @Override
    @DS("master")
    public List<OptionVO> queryTableItemList(final OptionDTO query) {
        // 支持 多数据库 类型
        return this.baseMapper.queryTableItemList(query);
    }

    /**
     * 数据字典 查询树形结构
     *
     * @param type - 类型名称
     * @return List<Dictionary> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dictionary", key = "'dict_by_type_' + #type")
    public List<Dictionary> queryDictionaryList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dictionary> dictionaryItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictionaryItemList)) {
                // 构建数据
                return new BuildingTreeData<Dictionary>().buildingTreeData(dictionaryItemList);
            }
            return dictionaryItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 数据字典 查询顶级 以及 所有的子级列表
     *
     * @param type - 类型名称
     * @return List<Dictionary> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dictionary", key = "'dict_top_and_all_child_by_type_' + #type")
    public List<Dictionary> queryDictionaryTopAndAllChildList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dictionary> dictionaryItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictionaryItemList)) {
                List<Dictionary> dataList = new ArrayList<>();
                // 构建数据
                dictionaryItemList.stream()
                        // 过滤出 顶级节点
                        .filter(item -> StringUtils.isEmpty(item.getParentId()) || StringPool.ZERO.equals(item.getParentId()))
                        // 排序
                        .sorted(Comparator.comparingDouble(Dictionary::getSortNo))
                        // 遍历顶级节点处理数据
                        .forEach(
                                parent -> {
                                    List<Dictionary> childList = new ArrayList<>();
                                    // 查找指定节点及下所有子节点
                                    new BuildingTreeData<Dictionary>().findAllData(Boolean.TRUE, childList, parent.getId(), dictionaryItemList);
                                    parent.setChildren(childList);
                                    dataList.add(parent);
                                }
                        );
                return dataList;
            }
            return dictionaryItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 数据字典 查询指定 以及 所有的子级列表
     *
     * @param type  - 类型名称
     * @param value - 字典数值
     * @return List<Dictionary> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dictionary", key = "'dict_list_by_type_' + #type + '_value_' + #value")
    public List<Dictionary> queryDictionaryListByValue(final String type, final String value) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dictionary> dictionaryItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictionaryItemList)) {
                List<Dictionary> dataList = new ArrayList<>();
                // 构建数据
                dictionaryItemList.stream()
                        // 过滤出 指定节点
                        .filter(item -> value.equals(item.getValue()))
                        // 排序
                        .sorted(Comparator.comparingDouble(Dictionary::getSortNo))
                        // 遍历顶级节点处理数据
                        .forEach(
                                parent -> {
                                    List<Dictionary> childList = new ArrayList<>();
                                    // 查找指定节点及下所有子节点
                                    new BuildingTreeData<Dictionary>().findAllData(Boolean.TRUE, childList, parent.getId(), dictionaryItemList);
                                    dataList.addAll(childList);
                                }
                        );
                return dataList;
            }
            return dictionaryItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 数据字典 查询所有，不分层级
     *
     * @param type - 类型名称
     * @return List<Dictionary> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dictionary", key = "'dict_all_by_type_' + #type")
    public List<Dictionary> queryAllDictionaryList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            return this.list();
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

}
