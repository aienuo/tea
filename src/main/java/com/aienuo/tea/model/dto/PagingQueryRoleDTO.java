package com.aienuo.tea.model.dto;

import com.aienuo.tea.common.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统角色 - 分页查询参数
 */
@Data
@Schema(description = "系统角色 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryRoleDTO extends BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    private String id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

}
