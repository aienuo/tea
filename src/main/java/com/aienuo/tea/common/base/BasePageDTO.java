package com.aienuo.tea.common.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 分页条件 基类
 */
@Data
@Schema(description = "分页条件")
@EqualsAndHashCode(callSuper = false)
public class BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long current;

    public Long getCurrent() {
        return this.current = current == null ? 1L : current;
    }

    /**
     * 页长
     */
    @Schema(description = "页长", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long size;

    public Long getSize() {
        return this.size = size == null ? 10L : size;
    }

    /**
     * 标识
     */
    @Schema(description = "标识")
    private String id;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creatorBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;
    @Schema(description = "创建时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updaterBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间-起")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTimeStart;
    @Schema(description = "更新时间-止")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTimeEnd;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private List<OrderItem> sortFieldList;

    /**
     * 自定义查询条件（key -  条件字段名， value - 条件值）
     */
    @Schema(description = "自定义查询条件（key -  条件字段名， value - 条件值）")
    private Map<String, Object> condition;

    /**
     * 超级查询参数 这个参数方便自定义SQL条件查询（要考虑SQL注入，查询条件与数据权限冲突）
     */
    @Schema(description = "超级查询参数", hidden = true, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String superQueryParams;

    /**
     * 获取 自定义差查询条件的 值
     *
     * @param key - 条件字段名
     * @return Object - 条件值
     */
    public Object get(String key) {
        return this.condition.get(key);
    }

    /**
     * 设置 自定义差查询条件
     *
     * @param key   -  条件字段名
     * @param value - 条件值
     */
    public void set(String key, Object value) {
        this.condition.put(key, value);
    }

    /**
     * 删除 自定义差查询条件
     *
     * @param key -  条件字段名
     */
    public void remove(String key) {
        this.condition.remove(key);
    }

}
