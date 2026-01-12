package com.aienuo.tea.service.impl;

import com.aienuo.tea.mapper.DictMapper;
import com.aienuo.tea.model.dto.DictQueryDTO;
import com.aienuo.tea.model.dto.OptionDTO;
import com.aienuo.tea.model.po.Dict;
import com.aienuo.tea.model.vo.OptionVO;
import com.aienuo.tea.service.IDictService;
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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    /**
     * 数据字典 查询树形结构
     *
     * @param query - 查询参数
     * @return List<Dict> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dict", keyGenerator = "redisKeyGenerator")
    public List<Dict> queryDictList(final DictQueryDTO query) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", query.getType());
            }});
            // 查询数据
            List<Dict> dictItemList = this.list(Wrappers.<Dict>lambdaQuery()
                    .eq(StringUtils.isNotEmpty(query.getLabel()), Dict::getLabel, query.getLabel())
                    .eq(StringUtils.isNotEmpty(query.getValue()), Dict::getValue, query.getValue())
            );
            if (CollectionUtils.isNotEmpty(dictItemList)) {
                if (StringUtils.isNotEmpty(query.getId())) {
                    List<Dict> dataList = new ArrayList<>();
                    // 构建数据
                    new BuildingTreeData<Dict>().findAllData(Boolean.FALSE, dataList, query.getId(), dictItemList);
                    return dataList;
                } else {
                    return new BuildingTreeData<Dict>().buildingTreeData(dictItemList);
                }
            }
            return dictItemList;
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
        // MySQL 数据库
//        return this.baseMapper.queryTableItemListForMySQL(query);
        // 达梦数据库
        return this.baseMapper.queryTableItemListForDM(query);
    }

    /**
     * 数据字典 查询树形结构
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dict", key = "'dict_by_type_' + #type")
    public List<Dict> queryDictList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dict> dictItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictItemList)) {
                // 构建数据
                return new BuildingTreeData<Dict>().buildingTreeData(dictItemList);
            }
            return dictItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 数据字典 查询顶级 以及 所有的子级列表
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dict", key = "'dict_top_and_all_child_by_type_' + #type")
    public List<Dict> queryDictTopAndAllChildList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dict> dictItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictItemList)) {
                List<Dict> dataList = new ArrayList<>();
                // 构建数据
                dictItemList.stream()
                        // 过滤出 顶级节点
                        .filter(item -> StringUtils.isEmpty(item.getParentId()) || StringPool.ZERO.equals(item.getParentId()))
                        // 排序
                        .sorted(Comparator.comparingDouble(Dict::getSortNo))
                        // 遍历顶级节点处理数据
                        .forEach(
                                parent -> {
                                    List<Dict> childList = new ArrayList<>();
                                    // 查找指定节点及下所有子节点
                                    new BuildingTreeData<Dict>().findAllData(Boolean.TRUE, childList, parent.getId(), dictItemList);
                                    parent.setChildren(childList);
                                    dataList.add(parent);
                                }
                        );
                return dataList;
            }
            return dictItemList;
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
     * @return List<Dict> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dict", key = "'dict_list_by_type_' + #type + '_value_' + #value")
    public List<Dict> queryDictListByValue(final String type, final String value) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            List<Dict> dictItemList = this.list();
            if (CollectionUtils.isNotEmpty(dictItemList)) {
                List<Dict> dataList = new ArrayList<>();
                // 构建数据
                dictItemList.stream()
                        // 过滤出 指定节点
                        .filter(item -> value.equals(item.getValue()))
                        // 排序
                        .sorted(Comparator.comparingDouble(Dict::getSortNo))
                        // 遍历顶级节点处理数据
                        .forEach(
                                parent -> {
                                    List<Dict> childList = new ArrayList<>();
                                    // 查找指定节点及下所有子节点
                                    new BuildingTreeData<Dict>().findAllData(Boolean.TRUE, childList, parent.getId(), dictItemList);
                                    dataList.addAll(childList);
                                }
                        );
                return dataList;
            }
            return dictItemList;
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

    /**
     * 数据字典 查询所有，不分层级
     *
     * @param type - 类型名称
     * @return List<Dict> - 数据字典
     */
    @Override
    @DS("master")
    @Cacheable(cacheNames = "dict", key = "'dict_all_by_type_' + #type")
    public List<Dict> queryAllDictList(final String type) {
        try {
            // 设置表后缀名
            RequestDataHelper.setRequestData(new HashMap<>() {{
                put("table_name", type);
            }});
            // 查询数据
            return  this.list();
        } finally {
            // 清理数据
            RequestDataHelper.removeRequestData();
        }
    }

}
