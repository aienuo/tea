package com.aienuo.tea.model.po;

import com.aienuo.tea.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_LOG")
public class Log extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Token
     */
    @TableField(value = "TOKEN")
    private String token;

    /**
     * 请求方法名
     */
    @TableField(value = "METHOD")
    private String method;

    /**
     * 访问地址
     */
    @TableField(value = "URL")
    private String url;

    /**
     * 请求参数
     */
    @TableField(value = "PARAMETERS")
    private String parameters;

    /**
     * 用户 IP
     */
    @TableField(value = "USER_IP")
    private String userIp;

    /**
     * 请求头
     */
    @TableField(value = "HEADER")
    private String header;

    /**
     * 请求开始时间
     */
    @TableField(value = "START_TIME")
    private LocalDateTime startTime;

    /**
     * 请求结束时间
     */
    @TableField(value = "END_TIME")
    private LocalDateTime endTime;

    /**
     * 执行时长，单位：毫秒
     */
    @TableField(value = "DURATION")
    private Long duration;

    /**
     * 错误码
     */
    @TableField(value = "CODE")
    private Integer code;

    /**
     * 结果提示
     */
    @TableField(value = "MESSAGE")
    private String message;

}
