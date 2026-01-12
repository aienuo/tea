package com.aienuo.tea.common.base;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Tree 对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseTree<T> extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 父级标识
     */
    @Schema(description = "父级标识")
    @TableField(value = "PARENT_ID", updateStrategy = FieldStrategy.ALWAYS)
    private String parentId;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    @TableField(value = "SORT_NO")
    private Double sortNo;

    /**
     * 子级列表（非数据库字段）
     */
    @Schema(description = "子级列表")
    @TableField(exist = false)
    private List<T> children;

}
