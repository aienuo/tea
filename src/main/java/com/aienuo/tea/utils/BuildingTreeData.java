package com.aienuo.tea.utils;

import com.aienuo.tea.common.base.BaseTree;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 构建树形数据
 *
 * @param <T>
 */
public class BuildingTreeData<T extends BaseTree<T>> {

    /**
     * 递归查找子节点
     *
     * @param paren    - 父对象
     * @param treeList - 集合对象
     * @return T - 对象
     */
    public T buildingChildData(final T paren, final List<T> treeList) {
        treeList.stream()
                // 过滤出 指定父级下的子节点
                .filter(item -> StringUtils.isNotEmpty(item.getParentId()) && paren.getId().equals(item.getParentId()))
                // 排序
                .sorted(Comparator.comparingDouble(T::getSortNo))
                // 循环处理子节点
                .forEach(
                        item -> {
                            //  2_1.判断父对象内的字段项为空时的操作
                            if (CollectionUtils.isEmpty(paren.getChildren())) {
                                paren.setChildren(new ArrayList<>());
                            }
                            //  2_1.构建指定父级下的子节点数据
                            paren.getChildren().add(buildingChildData(item, treeList));
                        }
                );
        return paren;
    }

    /**
     * 生成 Tree
     *
     * @param treeList - 对象集合
     * @return List<T> - Tree
     */
    public List<T> buildingTreeData(final List<T> treeList) {
        List<T> treeArrayList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(treeList)) {
            treeList.stream()
                    // 过滤出 顶级节点
                    .filter(item -> StringUtils.isEmpty(item.getParentId()) || StringPool.ZERO.equals(item.getParentId()))
                    // 排序
                    .sorted(Comparator.comparingDouble(T::getSortNo))
                    // 遍历顶级节点处理数据
                    .forEach(
                            item -> {
                                // 构建指定父级下的子节点数据
                                treeArrayList.add(buildingChildData(item, treeList));
                            }
                    );
        }
        return treeArrayList;
    }

    /**
     * 生成 Tree
     *
     * @param id       - 指定节点
     * @param treeList - 所有数据
     */
    public List<T> buildingTreeData(final String id, final List<T> treeList) {
        List<T> treeArrayList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(treeList)) {
            treeList.stream()
                    // 过滤出 顶级节点
                    .filter(item -> id.equals(item.getId()))
                    // 排序
                    .sorted(Comparator.comparingDouble(T::getSortNo))
                    // 遍历顶级节点处理数据
                    .forEach(
                            item -> {
                                // 构建指定父级下的子节点数据
                                treeArrayList.add(buildingChildData(item, treeList));
                            }
                    );
        }
        return treeArrayList;
    }

    /**
     * 查找指定节点及下所有子节点
     *
     * @param have     - 是否包含 指定节点
     * @param dataList - 数据集合
     * @param id       - 指定节点
     * @param treeList - 所有数据
     */
    public void findAllData(final Boolean have, List<T> dataList, final String id, final List<T> treeList) {
        if (CollectionUtils.isNotEmpty(treeList)) {
            // 指定 指定节点
            T paren = treeList.stream().filter(item -> Objects.equals(id, item.getId())).findFirst().orElse(null);
            if (paren != null) {
                if (have) {
                    // 包含 指定节点，深拷贝防止地址引用
                    dataList.add(SerializationUtils.clone(paren));
                }
                // 处理 指定节点及下的子节点
                treeList.stream()
                        // 过滤出 指定父级下的子节点
                        .filter(item -> StringUtils.isNotEmpty(item.getParentId()) && id.equals(item.getParentId()))
                        // 排序
                        .sorted(Comparator.comparingDouble(T::getSortNo))
                        // 遍历顶级节点处理数据
                        .forEach(
                                item -> {
                                    // 递归处理下一级
                                    findAllData(Boolean.TRUE, dataList, item.getId(), treeList);
                                }
                        );
            }
        }
    }

}
