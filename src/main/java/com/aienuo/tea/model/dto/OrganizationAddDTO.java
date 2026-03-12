package com.aienuo.tea.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 组织机构 - 添加参数
 */
@Data
@Schema(description = "组织机构 - 添加参数")
@EqualsAndHashCode(callSuper = false)
public class OrganizationAddDTO implements Serializable {

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
     * 番号
     */
    @Schema(description = "番号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "番号不能为空")
    private String name;

    /**
     * 简称
     */
    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "简称不能为空")
    private String abbreviation;

    /**
     * 类型
     */
    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类型不能为空")
    private String type;

    /**
     * 级别
     */
    @Schema(description = "级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "级别不能为空")
    private String grade;

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
    private Double longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    private Double latitude;

    /**
     * 海拔
     */
    @Schema(description = "海拔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "海拔不能为空")
    private Double altitude;

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
