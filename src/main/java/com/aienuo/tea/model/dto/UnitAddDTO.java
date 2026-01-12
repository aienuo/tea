package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 组织机构 - 添加参数
 */
@Data
@Schema(description = "组织机构 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class UnitAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构排序
     */
    @Schema(description = "组织机构排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "权限排序不能为空")
    private Double sortNo;

    /**
     * 父级标识
     */
    @Schema(description = "父级标识")
    private String parentId;

    /**
     * 部队番号
     */
    @Schema(description = "部队番号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "部队番号不能为空")
    private String name;

    /**
     * 部队简称
     */
    @Schema(description = "部队简称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "部队简称不能为空")
    private String abbreviation;

    /**
     * 战区内码
     */
    @Schema(description = "战区内码")
    @NotBlank(message = "战区内码不能为空")
    private String warZone;

    /**
     * 兵种内码
     */
    @Schema(description = "兵种内码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "兵种内码不能为空")
    private String militaryBranch;

    /**
     * 经度
     */
    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    /**
     * 海拔
     */
    @Schema(description = "海拔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "海拔不能为空")
    private BigDecimal altitude;

    /**
     * 地名内码
     */
    @Schema(description = "地名内码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "地名内码不能为空")
    private String areaCode;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;

}
