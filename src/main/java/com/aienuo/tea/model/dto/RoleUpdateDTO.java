package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统角色 - 角色更新参数
 */
@Data
@Schema(description = "系统角色 - 角色更新")
@EqualsAndHashCode(callSuper = false)
public class RoleUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    @Schema(description = "角色标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色标识不能为空")
    private String id;

    /**
     * 角色序号
     */
    @Schema(description = "角色序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色序号不能为空")
    private Double sortNo;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String description;

    /**
     * 菜单权限
     */
    @Schema(description = "菜单权限")
    private List<String> permissionList;

}
