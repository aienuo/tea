package com.aienuo.tea.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 接口文档 相关配置
 */
@Configuration
public class Knife4jConfig implements WebMvcConfigurer {

    /**
     * 文件本地存储路径（跟jar包同级目录，自动拼接 “./”）
     */
    @Value(value = "${server.upload.path}")
    private String uploadPath;

    /**
     * 添加资源处理程序
     *
     * @param resourceHandlerRegistry - 资源处理程序注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        // 本地文件上传-路径模式
        String pathPatterns = StringPool.SLASH + uploadPath + StringPool.SLASH + StringPool.ASTERISK + StringPool.ASTERISK;
        // 本地文件上传-文件资源位置（跟jar包同级目录，自动拼接 “./”）
        String resourceLocations = "file" + StringPool.COLON + StringPool.DOT + StringPool.SLASH + uploadPath + StringPool.SLASH;
        resourceHandlerRegistry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations);
        resourceHandlerRegistry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        resourceHandlerRegistry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        resourceHandlerRegistry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    /**
     * 跨域访问
     *
     * @param registry - Cors 注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源
                .allowedOriginPatterns("*")
                // 允许请求方法
                .allowedMethods(RequestMethod.GET.name(), RequestMethod.HEAD.name(), RequestMethod.POST.name(), RequestMethod.PUT.name(), RequestMethod.DELETE.name(), RequestMethod.OPTIONS.name(), RequestMethod.TRACE.name())
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*")
                // 是否允许证书（Cookies）
                .allowCredentials(true);
    }

    /**
     * 接口文档
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI Swagger3() {
        return new OpenAPI()
                .info(new Info().title("训练保障资源统筹调配分析应用（资源保障）")
                        .description("训练保障资源统筹调配分析应用（资源保障）系统服务接口文档") // 描述
                        .version("0.0.1") // 版本号
                        .termsOfService("http://aienuo.com") // 服务条款
                        .contact(new Contact().name("SanJin").url("http://aienuo.com").email("aienuo@qq.com")) // 联系人信息
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))) // 许可证信息
                .externalDocs(new ExternalDocumentation().description("训练保障资源统筹调配分析应用（资源保障）系统服务接口文档").url("http://aienuo.com"))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                        .name(HttpHeaders.AUTHORIZATION)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer ")
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .description("Token")
                ))
                ;
    }

}