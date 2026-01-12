## 典型应用 Typical Application 后端服务

### TYPICAL_APPLICATION

* 某项目典型应用后端开发模板，已对接 项目集成平台单点登录，有后台管理功能

## 一、系统配置

* 1、`Maven`选用`apache-maven-3.6.3`版本，`JDK`使用`jdk-21`，数据库使用`DM8`，缓存使用`Redis`
* 2、解决`properties`文件中文不正常显示问题： 点击菜单栏 `File` 选择 `Settings...`， 找到 `Editor` 下的 `File Encoding`菜单，勾选上`Transparent native-to-ascii conversion`选项
* 3、全局 key 配置到 启动命令里: `--mpw.key=9jubPvDU84wkzOm4` 
* 4、配置 jvm 启动参数: `-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8012 -Dcom.sun.management.jmxremote.rmi.port=8012 -Djava.rmi.server.hostname=120.130.140.200 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false`