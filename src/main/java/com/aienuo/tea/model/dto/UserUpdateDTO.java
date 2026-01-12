package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户 - 用户更新参数
 */
@Data
@Schema(description = "系统用户 - 用户更新参数")
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 用户标识
     */
    @Schema(description = "用户标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户标识不能为空")
    private String id;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名称不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "用户名称不能为空")
    private String realName;

    /**
     * 单位标识
     */
    @Schema(description = "单位标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "单位标识不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "单位标识不能为空")
    private String unitId;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleList;

}
