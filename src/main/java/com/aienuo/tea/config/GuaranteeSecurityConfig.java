package com.aienuo.tea.config;

import com.aienuo.tea.business.SecurityServiceImpl;
import com.aienuo.tea.common.filter.TokenAuthenticationFilter;
import com.aienuo.tea.common.handlers.GlobalResponseBodyHandler;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import jakarta.annotation.Resource;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

/**
 * 安全 配置
 */
@Configuration
@EnableWebSecurity
public class GuaranteeSecurityConfig {

    /**
     * 中台鉴权实现类
     */
    @Resource
    private SecurityServiceImpl securityService;

    /**
     * 加密
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 请求
     *
     * @param restTemplateBuilder - RestTemplateBuilder
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    /**
     * 自定义认证失败处理器
     *
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StringPool.UTF_8);
            response.getWriter().write("温馨提示：该操作需要登陆之后进行！");
        };
    }

    /**
     * 登录时需要调用 AuthenticationManager.authenticate 执行一次校验
     *
     * @param config - AuthenticationConfiguration
     * @return AuthenticationManager -
     * @throws Exception - 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 设置 指定路径 不走鉴权
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()// 允许所有OPTIONS请求
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                // 放行 Knife4j 核心路径
                .requestMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/fonts/**",
                        "/index.html",
                        "/favicon.ico",
                        "/static/favicon.ico",
                        "/doc.html",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/**",
                        "/index/login"
                );
    }

    /**
     * Security 安全验证
     *
     * @param http - HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception - 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    // 禁用默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出页面
                .logout(AbstractHttpConfigurer::disable)
                // 禁用 Session
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 禁用 Basic
                .httpBasic(AbstractHttpConfigurer::disable)
                // 设置权限
                .authorizeHttpRequests(
                        authorizationRequests -> authorizationRequests
                                // 允许所有OPTIONS请求
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // 放行 Knife4j 核心路径
                                .requestMatchers(
                                        "/js/**",
                                        "/css/**",
                                        "/img/**",
                                        "/fonts/**",
                                        "/index.html",
                                        "/favicon.ico",
                                        "/static/favicon.ico",
                                        "/doc.html",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-resources/**",
                                        "/v3/**",
                                        "/index/login"
                                )
                                .permitAll()
                                // 拦截其他所有请求
                                .anyRequest().authenticated()
                )
                // 允许 跨域访问 CORS
                .cors(AbstractHttpConfigurer::disable)
                // 前后端分离项目 关闭 CSRF 保护
                .csrf(AbstractHttpConfigurer::disable)
                // 自定义认证失败处理器
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
                })
                // 鉴权过滤器
                .addFilterBefore(new TokenAuthenticationFilter(securityService), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    /**
     * 全局响应结果（ResponseBody）处理器
     *
     * @return GlobalResponseBodyHandler
     */
    @Bean
    public GlobalResponseBodyHandler globalResponseBodyHandler() {
        return new GlobalResponseBodyHandler();
    }

}
