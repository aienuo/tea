package com.aienuo.tea;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class TeaApplication {

    public static void main(String[] args) {
        ConfigurableEnvironment configurableEnvironment = SpringApplication.run(TeaApplication.class, args).getEnvironment();
        String applicationName = configurableEnvironment.getProperty("spring.application.name");
        // 获取主机地址
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "127.0.0.1";
            log.warn("无法获取主机IP,使用默认地址: {}", hostAddress);
        }
        String serverPort = configurableEnvironment.getProperty("server.port");
        if (serverPort == null) {
            serverPort = "8080";
            log.warn("无法获取主机Port,使用默认端口: {}", serverPort);
        }
        log.info("""
                        \r🚀----------------------------------------------------------🚀
                        Application '{}' is running success!
                        接口文档访问地址:
                        本地Knife4j地址:   http://localhost:{}/doc.html
                        外部Swagger地址:   http://{}:{}/swagger-ui/index.html
                        🚀----------------------------------------------------------🚀""",
                applicationName,
                serverPort,
                hostAddress,
                serverPort
        );
    }

}
