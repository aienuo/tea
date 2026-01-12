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
     * 番号
     */
    @TableField(value = "BDFH")
    private String name;

    /**
     * 简称
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
     * 类型
     */
    @TableField(value = "BDLX")
    private String type;

    /**
     * 级别
     */
    @TableField(value = "BDJB")
    private String grade;

    /**
     * 经度
     */
    @TableField(value = "ZBJD")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "ZBWD")
    private BigDecimal latitude;

    /**
     * 海拔
     */
    @TableField(value = "ZBHB")
    private BigDecimal altitude;

    /**
     * 地名内码
     */
    @TableField(value = "DMNM")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField(value = "XXDZ")
    private String address;

}
