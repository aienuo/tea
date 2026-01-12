#!/bin/sh
jarName=tea-1.0.0.jar
JVM_OPTS="-Dname=$jarName  -Duser.timezone=Asia/Shanghai -Xms512M -Xmx1024M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=81 -Dcom.sun.management.jmxremote.rmi.port=81 -Djava.rmi.server.hostname=120.130.140.200 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
nohup java -jar  $JVM_OPTS /usr/local/tea_server/$jarName --mpw.key=9jubPvDU84wkzOm4 > /dev/null 2>&1 &
