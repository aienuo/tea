package com.aienuo.tea.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties
@Schema(description = "中台用户字段")
public class SsoUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /* 失败情况返回值 start */
    @Schema(description = "返回结果")
    private String result;

    @Schema(description = "返回结果说明")
    private String rsltmsg;

    @Schema(description = "返回码")
    private String rsltcode;
    /* 失败情况返回值 end */

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "密码（密文）")
    private String password;

    @Schema(description = "用户名全称")
    private String fullname;

    @Schema(description = "证件号码")
    private String idString;

    @Schema(description = "性别（0女1男）")
    private String sex;

    @Schema(description = "电话")
    private String mobile;

    @Schema(description = "用户有效期开始时间")
    private String startDate;

    @Schema(description = "用户有效期截止时间")
    private String endDate;

    @Schema(description = "登录模式（允许通过用户名口令时为100，允许通过证书模式时为010，允许通过口令+证书模式时为110）")
    private String loginMode;

    @Schema(description = "是否禁用")
    private Boolean isDelete;

    @Schema(description = "用户机构关系集合")
    private List<Relation> relation;

    /**
     * 用户机构关系
     */
    @Data
    @Schema(description = "用户机构关系")
    public static class Relation implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @Schema(description = "机构 id")
        private String organId;

        @Schema(description = "机构名称")
        private String orgName;

        @Schema(description = "用户在当前机构的排序号")
        private String orderId;

        @Schema(description = "职务")
        private String duty;

        @Schema(description = "主副部门（1：主部门；0：副部门）")
        private Integer priOrgan;

    }

}
