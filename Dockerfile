# 指定父镜像 设置 JAVA 版本 (指定dockerfile基于那个image 构建)
FROM eclipse-temurin:21-jdk-alpine

# 用来标明 dockerfile 的标签
LABEL org.opencontainers.image.authors='SanJin' name="tea"

# 设置时区为中国时区
ENV TZ=Asia/Shanghai

# 设置工作目录 (使用标准的系统应用目录)
WORKDIR /usr/local/tea

# 执行命令 执行一段命令 默认是/bin/sh 格式：RUN command 或者 RUN ["command" , "param1","param2"]
# RUN

# 指定存储卷，用于存放临时文件和上传文件
VOLUME /usr/local/tea/data

# 拷贝运行 JAR 包 (修正路径为相对路径)
COPY target/tea-0.0.1.jar app.jar

# 设置 JVM 运行参数，这里限定下内存大小，减少开销
ENV JAVA_OPTS="\
-server \
-Xms256m \
-Xmx512m \
-XX:MetaspaceSize=256m \
-XX:MaxMetaspaceSize=512m"

# 空参数，方便创建容器时传参 (敏感信息建议运行时注入)
ENV PARAMS=""

# 入口点，执行 JAVA 运行命令
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS app.jar $PARAMS"]

# 暴露端口 (Spring Boot 默认使用 TCP 协议)
EXPOSE 80/tcp

# 健康检查 (需要应用启动后提供健康检查端点)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:80/actuator/health || exit 1

# 通过环境变量注入 mpw.key 运行容器时指定上传路径
#docker run -d \
#  -p 80:80 \
#  -v tea-data:/usr/local/tea/data \
#  -e PARAMS="--mpw.key=9jubPvDU84wkzOm4 --server.upload.path=/usr/local/tea/data" \
#  --name tea-container \
#  tea:latest

