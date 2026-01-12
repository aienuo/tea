package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单权限 - 修改参数
 */
@Data
@Schema(description = "菜单权限 - 修改参数")
@EqualsAndHashCode(callSuper = false)
public class PermissionUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单权限标识
     */
    @Schema(description = "菜单权限标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限标识不能为空")
    private String id;

    /**
     * 权限排序
     */
    @Schema(description = "权限排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "权限排序不能为空")
    private Double sortNo;

    /**
     * 父级标识
     */
    @Schema(description = "父级标识")
    private String parentId;

    /**
     * 权限类型（01-主菜单，02-子菜单，03-按钮权限）
     */
    @Schema(description = "权限类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限类型不能为空")
    private String type;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /**
     * URL（标识）
     */
    @Schema(description = "URL（标识）")
    @NotBlank(message = "URL（标识不能为空")
    private String uri;

    /**
     * 菜单图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 菜单描述
     */
    @Schema(description = "菜单描述")
    private String description;

}
