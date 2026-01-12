package com.aienuo.tea.model.dto;

import com.aienuo.tea.common.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户 - 分页查询参数
 */
@Data
@Schema(description = "系统用户 - 分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class PagingQueryUserDTO extends BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单位标识
     */
    @Schema(description = "单位标识")
    @NotBlank(message = "单位标识不能为空")
    private String unitId;

    /**
     * 用户标识
     */
    @Schema(description = "用户标识")
    private String id;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    private String username;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String identityNumber;

    /**
     * 删除状态（0-正常，1-已删除）
     */
    @Schema(description = "删除状态（0-正常，1-已删除）")
    private Integer delFlag;

    /**
     * 数据分析单位列表
     */
    @Schema(description = "数据分析单位列表（当前单位标识及所有子级单位标识）", hidden = true)
    @Getter
    private List<String> unitIdList;

}
