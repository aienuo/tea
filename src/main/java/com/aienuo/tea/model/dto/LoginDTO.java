package com.aienuo.tea.model.dto;

import com.aienuo.tea.utils.TokenUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@JsonIgnoreProperties
@Schema(description = "用户登录")
public class LoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名称")
    @NotBlank(message = "用户名称不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "用户名称不能为空")
    private String username;

    @Schema(description = "用户密码（密文）")
    @NotBlank(message = "用户密码（密文）不能为空")
    @Pattern(regexp = "^(?!\s*$)(?!.*\s)(?!null$)[^\s]+$", message = "用户密码（密文）不能为空")
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
