package com.aienuo.tea.common.base;

import com.aienuo.tea.common.enums.CommonResponseEnum;
import com.aienuo.tea.common.enums.IResponseEnum;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用 返回值
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "返回值", description = "返回值")
public class BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    @Schema(description = "返回码")
    protected Integer code;

    /**
     * 返回信息
     */
    @Schema(description = "返回信息")
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    /**
     * @param responseEnum - IResponseEnum
     */
    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    /**
     * @param responseEnum - IResponseEnum
     */
    public BaseResponse(IResponseEnum responseEnum, String message) {
        this(responseEnum.getCode(), responseEnum.getMessage() + StringPool.COLON + message);
    }

    /**
     * @param code    - CODE
     * @param message - MESSAGE
     */
    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
