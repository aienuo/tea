package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询条件
 */
@Data
@Schema(description = "字典类型查询")
public class OptionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String label;

    /**
     * 数值
     */
    @Schema(description = "数值")
    private String value;

}
