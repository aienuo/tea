package com.aienuo.tea.common.response;

import com.aienuo.tea.common.base.BaseResponse;
import com.aienuo.tea.common.enums.CommonResponseEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 错误返回结果
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "错误返回结果")
public class ErrorResponse extends BaseResponse {

    /**
     * 错误返回结果
     *
     * @param message - 返回消息
     */
    public ErrorResponse(String message) {
        // 默认创建失败的回应
        super(CommonResponseEnum.ERROR, message);
    }

    /**
     * 错误返回结果
     *
     * @param code    - 返回代码
     * @param message - 返回消息
     */
    public ErrorResponse(Integer code, String message) {
        super(code, message);
    }

}

