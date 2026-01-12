package com.aienuo.tea.model.dto;

import com.aienuo.tea.utils.TokenUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * 系统用户 - 用户添加参数
 */
@Data
@Schema(description = "系统用户 - 用户添加参数")
@EqualsAndHashCode(callSuper = false)
public class UserAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "登录账号不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "登录账号不能为空")
    private String username;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "登录密码不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "登录密码不能为空")
    private String password;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名称不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "用户名称不能为空")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)")
    private String identityNumber;

    /**
     * 单位标识
     */
    @Schema(description = "单位标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "单位标识不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "单位标识不能为空")
    private String unitId;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleList;

    /**
     * 获取前端密码
     *
     * @return String - 前端密码
     */
    public String getPassword() {
        // 1、获取前端输入（接口入参）密码
        String password =  new String(Base64.getDecoder().decode(this.password), StandardCharsets.UTF_8);
        // 2、数据库存储密码
        return TokenUtils.encodePassword(password);
    }

}
