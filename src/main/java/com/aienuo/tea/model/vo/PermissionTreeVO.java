package com.aienuo.tea.model.vo;

import com.aienuo.tea.common.base.BaseTree;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单权限 - 树查询

 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "菜单权限 - 树查询返回值")
public class PermissionTreeVO extends BaseTree<PermissionTreeVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限标识（01-主菜单，02-子菜单，03-按钮权限）
     */
    @Schema(description = "权限标识")
    private String type;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * URL（标识）
     */
    @Schema(description = "URL（标识）")
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
