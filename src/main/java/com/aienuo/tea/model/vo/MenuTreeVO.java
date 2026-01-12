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
 * 菜单树 返回值
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "菜单树 - 返回值")
public class MenuTreeVO extends BaseTree<MenuTreeVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
