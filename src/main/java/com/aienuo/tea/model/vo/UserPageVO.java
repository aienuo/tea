package com.aienuo.tea.model.vo;

import com.aienuo.tea.utils.IdCardUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 系统用户 - 分页查询返回值
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "系统用户 - 分页查询返回值 对象")
public class UserPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 身份证号码
     */
    @Schema(description = "身份证号码")
    private String identityNumber;

    /**
     * 单位标识
     */
    @Schema(description = "单位标识")
    private String unitId;

    /**
     * 单位名称
     */
    @Schema(description = "单位名称")
    private String unitName;

    /**
     * 单位节点
     */
    @Schema(description = "单位节点")
    private String unitNode;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 年龄
     */
    @Schema(description = "年龄")
    @Setter(AccessLevel.NONE)
    private Integer age;

    /**
     * 性别(2-默认未知，1-男，0-女)
     */
    @Schema(description = "性别(2-默认未知，1-男，0-女)")
    @Setter(AccessLevel.NONE)
    private Integer sex;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    @Schema(description = "冻结状态(0-正常，1-冻结）")
    private Integer delFlag;

    /**
     * 角色集合
     */
    @Schema(description = "角色集合")
    private List<Role> roleList;

    /**
     * 系统用户 - 系统角色
     **/
    @Data
    @Schema(description = "系统用户 - 角色信息查询返回值")
    public static class Role implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 角色标识
         */
        @Schema(description = "标识")
        private String id;

        /**
         * 角色名称
         */
        @Schema(description = "名称")
        private String name;

    }


    /**
     *
     * @param identityNumber - 身份证号码
     */
    public void setIdentityNumber(String identityNumber) {
        if (StringUtils.isNotBlank(identityNumber)) {
            this.identityNumber = identityNumber;
            this.birthday = IdCardUtils.getBirthByIdCard(identityNumber);
            this.age = IdCardUtils.getAge(identityNumber);
            this.sex = IdCardUtils.getSex(identityNumber);
        }
    }

}
