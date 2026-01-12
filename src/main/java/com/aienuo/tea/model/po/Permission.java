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
 * 菜单权限
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_PERMISSION")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission extends BaseTree<Permission> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField(value = "MC")
    private String name;

    /**
     * 类型标识
     */
    @TableField(value = "LX")
    private String type;

    /**
     * URL（标识）
     */
    @TableField(value = "URI")
    private String uri;

    /**
     * 图标
     */
    @TableField(value = "TB")
    private String icon;

    /**
     * 描述
     */
    @TableField(value = "MS")
    private String description;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     *
     * @TableLogic(value = "0")
     */
    @TableField(value = "LJSC")
    private Integer delFlag;

}
