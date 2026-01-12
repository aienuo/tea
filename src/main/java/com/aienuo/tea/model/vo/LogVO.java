package com.aienuo.tea.model.vo;

import com.aienuo.tea.utils.TokenUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "系统日志-返回值")
public class LogVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "系统日志编号")
    private String id;

    @Schema(description = "Token")
    private String token;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "请求方法名")
    private String method;

    @Schema(description = "访问地址")
    private String url;

    @Schema(description = "请求参数")
    private String parameters;

    @Schema(description = "用户 IP")
    private String userIp;

    @Schema(description = "请求头")
    private String header;

    @Schema(description = "请求开始时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "请求结束时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "执行时长(单位：毫秒)")
    private Long duration;

    @Schema(description = "错误码")
    private Integer code;

    @Schema(description = "结果提示")
    private String message;

    @Schema(description = "创建人")
    private String creatorBy;

    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public String getUsername() {
        String username = "";
        if (StringUtils.isNotBlank(this.getToken())) {
            // 解析 username 适配集成平台
            username = TokenUtils.getUsernameByToken(token);
        }
        return username;
    }

}
