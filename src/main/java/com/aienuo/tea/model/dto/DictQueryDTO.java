package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "数据字典查询")
public class DictQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类型名称不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "类型名称不能为空")
    private String type;

    @Schema(description = "标识")
    private String id;

    @Schema(description = "名称")
    private String label;

    @Schema(description = "数值")
    private String value;

}
