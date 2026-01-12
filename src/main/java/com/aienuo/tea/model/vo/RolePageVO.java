package com.aienuo.tea.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "系统角色 - 分页查询返回值")
public class RolePageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    @Schema(description = "标识")
    private String id;

    /**
     * 角色名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 序号
     */
    @Schema(description = "序号")
    private Double sortNo;

    /**
     * 角色描述
     */
    @Schema(description = "描述")
    private String description;

}
