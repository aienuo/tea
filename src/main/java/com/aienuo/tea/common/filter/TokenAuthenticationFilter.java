package com.aienuo.tea.common.filter;

import com.aienuo.tea.business.SecurityServiceImpl;
import com.aienuo.tea.common.base.BaseResponse;
import com.aienuo.tea.model.po.Log;
import com.aienuo.tea.model.po.User;
import com.aienuo.tea.utils.IPV4Utils;
import com.aienuo.tea.utils.RequestDataHelper;
import com.aienuo.tea.utils.SecurityFrameworkUtils;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 鉴权过滤器
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 中台鉴权实现类
     */
    private final SecurityServiceImpl securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获得开始时间
        LocalDateTime startTime = LocalDateTime.now();
        Map<String, String> parameterMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        String token = "";
        try {
            // 提前获得参数，避免 XssFilter 过滤处理
            parameterMap = this.getParameterMap(request);
            // 获取请求头
            headerMap = this.getHeaderMap(request);
            token = getToken(headerMap, parameterMap);
            // 调用三方接口通过 token 获取用户信息
            if (StringUtils.isNotBlank(token)) {
                User user = this.securityService.getUserDataFromToken(token);
                log.info("用户信息：{}", user);
                if (user != null) {
                    // 校验 URL权限 是否合法
                    this.securityService.verificationPermissions(request, user);
                    // 设置当前用户 到 Authentication 上下文
                    SecurityFrameworkUtils.setLoginUser(user, request);
                    // 设置 Token 到缓存 过期时间默认一小时
                    this.securityService.setToken(token);
                    // 继续过滤链
                    filterChain.doFilter(request, response);
                    // 正常执行，记录日志
                    createLog(request, startTime, token, parameterMap, headerMap, null);
                }
            } else {
                RuntimeException exception = new RuntimeException("身份 token 不合法，请检查 token 是否存在");
                // 异常执行，记录日志
                createLog(request, startTime, token, parameterMap, headerMap, exception);
                // 此处抛出异常
                throw exception;
            }
        } catch (IOException | ServletException | RuntimeException exception) {
            // 异常执行，记录日志
            createLog(request, startTime, token, parameterMap, headerMap, exception);
            throw exception;
        }
    }

    /**
     * 从 HttpServletRequest 内 获取 ParameterMap
     *
     * @param request - HttpServletRequest
     * @return - Map<String, String> - 参数
     */
    private Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : Collections.unmodifiableMap(request.getParameterMap()).entrySet()) {
            parameterMap.put(entry.getKey(), Arrays.toString(entry.getValue()));
        }
        return parameterMap;
    }

    /**
     * 从 HttpServletRequest 内 获取 HeaderMap
     *
     * @param request - HttpServletRequest
     * @return - Map<String, String> - 参数
     */
    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        // HeaderName
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String name = requestHeaderNames.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        return headerMap;
    }

    /**
     * 获取 Token
     *
     * @param headerMap    - HeaderMap
     * @param parameterMap - ParameterMap
     * @return String - Token
     */
    private String getToken(final Map<String, String> headerMap, final Map<String, String> parameterMap) {
        // 从 url 获取三方 token 参数
        String authorization = headerMap.get(HttpHeaders.AUTHORIZATION.toLowerCase());
        // 应用的身份，是应用的全局唯一凭证，需通过应用id（client_id）及秘钥（client_secret）
        authorization = StringUtils.isNotBlank(authorization) ? authorization : headerMap.get("access_token");
        if (StringUtils.isBlank(authorization)) {
            String accessToken = parameterMap.get("access_token");
            authorization = StringUtils.isNotBlank(accessToken) ? accessToken : "";
        }
        return authorization.replaceAll(SecurityFrameworkUtils.AUTHORIZATION_BEARER, "");
    }

    /**
     * 从 HttpServletRequest 内 获取 body
     *
     * @param request - HttpServletRequest
     * @return - String - 参数
     */
    private String getBodyString(HttpServletRequest request) {
        StringBuilder body = new StringBuilder();
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
            } catch (IOException e) {
                log.error("获取 请求体失败 ：{}", e.getMessage(), e);
            }
        }
        return body.toString();
    }

    /**
     * 保存日志
     *
     * @param request      - HttpServletRequest
     * @param startTime    - LocalDateTime 开始时间
     * @param token        - Token
     * @param parameterMap - 请求参数
     * @param headerMap    - 请求头
     * @param exception    - Exception 异常
     */
    private void createLog(HttpServletRequest request, final LocalDateTime startTime, final String token, final Map<String, String> parameterMap, final Map<String, String> headerMap, final Exception exception) {
        Log logData = new Log();
        try {
            // 参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("query", parameterMap);
            // 请求体
            requestParams.put("body", this.getBodyString(request));
            // 获得结束时间
            LocalDateTime endTime = LocalDateTime.now();
            // 时差
            long duration = Duration.between(startTime, endTime).toMillis();
            // 异常信息
            int code = 200;
            String message = "正常访问";
            //  响应体
            BaseResponse responseBody = RequestDataHelper.getRequestData("responseBody");
            if (responseBody != null) {
                code = responseBody.getCode();
                message = responseBody.getMessage();
            } else if (exception != null) {
                code = 500;
                message = String.format("【%s】：【%s】", exception.getClass().getSimpleName(), exception.getMessage());
            }
            // 日志信息
            logData.setToken(token).setMethod(request.getMethod()).setUrl(request.getRequestURI()).setParameters(JSONUtils.toJSONString(requestParams)).setUserIp(IPV4Utils.getClientIpAddress(request)).setHeader(JSONUtils.toJSONString(headerMap)).setStartTime(startTime).setEndTime(LocalDateTime.now()).setDuration(duration).setCode(code).setMessage(message);
            // 保存日志信息
            logData.insert();
        } catch (Throwable throwable) {
            log.error("[insertErrorLog][url({}) log({}) 发生异常]", request.getRequestURI(), logData, throwable);
        }
        // 清除数据
        RequestDataHelper.removeRequestData();
    }

}
