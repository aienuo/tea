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

/**
 * 系统用户 - 密码重置参数
 */
@Data
@Schema(description = "系统用户 - 密码重置参数")
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户标识不能为空")
    private String id;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "密码不能为空")
    private String password;

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
