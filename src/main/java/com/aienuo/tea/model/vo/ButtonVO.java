package com.aienuo.tea.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 按钮权限 - 返回值
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "按钮权限 - 返回值")
public class ButtonVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 按钮标识
     */
    @Schema(description = "按钮标识")
    private String id;

    /**
     * 序号
     */
    @Schema(description = "序号")
    private Double sortNo;

    /**
     * 按钮名称（textName）
     */
    @Schema(description = "按钮名称（textName）")
    private String name;

    /**
     * 按钮（name）
     */
    @Schema(description = "按钮（name）")
    private String uri;

    /**
     * 按钮图标
     */
    @Schema(description = "按钮图标")
    private String icon;

    /**
     * 按钮描述
     */
    @Schema(description = "按钮描述")
    private String description;

}
