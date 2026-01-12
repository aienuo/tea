package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统角色
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_ROLE")
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 排序号
     */
    @TableField(value = "SORT_NO")
    private Double sortNo;

    /**
     * 名称
     */
    @TableField(value = "MC")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "MS")
    private String description;

}
