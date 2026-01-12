package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseTree;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 组织机构
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_UNIT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Unit extends BaseTree<Unit> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部队番号
     */
    @TableField(value = "BDFH")
    private String name;

    /**
     * 部队简称
     */
    @TableField(value = "BDJC")
    private String abbreviation;

    /**
     * 战区内码
     */
    @TableField(value = "ZQNM")
    private String warZone;

    /**
     * 兵种内码
     */
    @TableField(value = "BZNM")
    private String militaryBranch;

    /**
     * 经度
     */
    @TableField(value = "LONGITUDE")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "LATITUDE")
    private BigDecimal latitude;

    /**
     * 海拔
     */
    @TableField(value = "ALTITUDE")
    private BigDecimal altitude;

    /**
     * 地名内码
     */
    @TableField(value = "DMNM")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField(value = "DETAILED_ADDRESS")
    private String address;

}
