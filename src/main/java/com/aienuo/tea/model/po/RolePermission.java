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
 * 角色权限关系
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_ROLE_PERMISSION")
public class RolePermission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统角色标识
     */
    @TableField(value = "JS")
    private String role;

    /**
     * 菜单权限标识
     */
    @TableField(value = "CDQX")
    private String permission;

}
