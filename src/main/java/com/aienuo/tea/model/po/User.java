package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_USER")
public class User extends BaseEntity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 身份证号
     */
    @TableField(value = "identity_number")
    private String identityNumber;

    /**
     * 组织机构标识
     */
    @TableField(value = "unit_id")
    private String unitId;

    /**
     * 删除状态（0-正常，1-已删除）
     * 强制要求逻辑删除的注解字段要放在最后
     *
     * @TableLogic(value = "0")
     */
    @TableField(value = "del_flag")
    private Integer delFlag;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 是否未过期
     *
     * @return Boolean
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.delFlag == 0;
    }

    /**
     * 是否未锁定
     *
     * @return Boolean
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.delFlag == 0;
    }

    /**
     * 凭证未过期
     *
     * @return Boolean
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * 是否可用
     *
     * @return Boolean
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.delFlag == 0;
    }

}