package com.aienuo.tea.common.response;

import com.aienuo.tea.common.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回值
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<T> extends BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "数据")
    protected T data;

    public CommonResponse() {
        super();
    }

    /**
     * 通用返回值
     *
     * @param data - T 返回值数据
     */
    public CommonResponse(T data) {
        super();
        this.data = data;
    }

}
