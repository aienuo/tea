package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseTree;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据字典
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_DICT_ITEM_")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dict extends BaseTree<Dict> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField(value = "ITEM_LABEL")
    private String label;

    /**
     * 数值
     */
    @TableField(value = "ITEM_VALUE")
    private String value;

    /**
     * 扩展字段
     */
    @TableField(value = "ITEM_EXPAND")
    private String expand;

    /**
     * 描述
     */
    @TableField(value = "ITEM_DESCRIPTION")
    private String description;

}
