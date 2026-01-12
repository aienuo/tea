package com.aienuo.tea.model.vo;

import com.aienuo.tea.common.base.BaseTree;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 组织机构 - 树查询

 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "组织机构 - 树查询返回值")
public class UnitTreeVO extends BaseTree<UnitTreeVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 番号
     */
    @Schema(description = "番号")
    private String name;

    /**
     * 简称
     */
    @Schema(description = "简称")
    private String abbreviation;

    /**
     * 战区内码
     */
    @Schema(description = "战区内码")
    private String warZone;

    /**
     * 兵种内码
     */
    @Schema(description = "兵种内码")
    private String militaryBranch;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /**
     * 海拔
     */
    @Schema(description = "海拔")
    private BigDecimal altitude;

    /**
     * 地名内码
     */
    @Schema(description = "地名内码")
    private String areaCode;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;

}
