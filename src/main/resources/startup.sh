#!/bin/sh

# 基础配置
JAVA_HOME="/usr/local/jdk-17.0.15+6"  # JDK安装目录
JAR_DIR="/usr/local/tea_server"				# JAR包存放目录
JAR_NAME="tea-1.0.0.jar"              # JAR包名称
JVM_OPTS="-Dname=$JAR_NAME  -Duser.timezone=Asia/Shanghai -Xms512M -Xmx1024M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=81 -Dcom.sun.management.jmxremote.rmi.port=81 -Djava.rmi.server.hostname=120.130.140.200 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

# 启动命令
nohup java -jar  $JVM_OPTS $JAR_DIR/$JAR_NAME --mpw.key=9jubPvDU84wkzOm4 > /dev/null 2>&1 &
