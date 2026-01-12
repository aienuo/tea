package com.aienuo.tea.common.handlers;

import com.aienuo.tea.common.base.BaseResponse;
import com.aienuo.tea.utils.RequestDataHelper;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局响应结果（ResponseBody）处理器，记录 Controller 的返回结果， 方便 {@link com.aienuo.tea.common.filter.TokenAuthenticationFilter} 记录访问日志
 */
@SuppressWarnings("rawtypes")
@Hidden
@RestControllerAdvice(basePackages = {"com.aienuo.tea.controller"})
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

    /**
     * 拦截返回结果为 BaseResponse 类型 的函数
     *
     * @param returnType    - 方法参数
     * @param converterType - 类
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return Boolean.FALSE;
        }
        // 只拦截返回结果为 BaseResponse 类型
        return returnType.getMethod().getReturnType().getSuperclass() == BaseResponse.class;
    }

    /**
     * 之前
     *
     * @param body                  - 请求体
     * @param returnType            - 方法参数
     * @param selectedContentType   - MediaType
     * @param selectedConverterType - Class
     * @param request               - ServerHttpRequest
     * @param response              - ServerHttpResponse
     * @return Object
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 记录 Controller 结果
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("responseBody", body);
        RequestDataHelper.setRequestData(dataMap);
        return body;
    }

}
