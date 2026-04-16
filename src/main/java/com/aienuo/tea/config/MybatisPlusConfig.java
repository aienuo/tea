package com.aienuo.tea.config;

import com.aienuo.tea.common.interceptor.AutoPaginationInnerInterceptor;
import com.aienuo.tea.utils.RequestDataHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameJsqlParserInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;

/**
 * MybatisPlus 配置
 */
@Slf4j
@Configuration
@MapperScan("com.aienuo.tea.mapper")
public class MybatisPlusConfig {

    /**
     * 配置 DatabaseIdProvider
     *
     * @return VendorDatabaseIdProvider
     */
    @Bean
    public VendorDatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        // 键为数据库驱动返回的 DatabaseProductName（严格匹配 JDBC 驱动返回的数据库产品名称），值为自定义的别名
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("PostgreSQL", "postgresql");
        properties.setProperty("DM DBMS", "dm");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

    /**
     * 配置
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件（自定义的动态方言拦截器）
        interceptor.addInnerInterceptor(new AutoPaginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 动态表名插件
        interceptor.addInnerInterceptor(new DynamicTableNameJsqlParserInnerInterceptor(
                (sql, tableName) -> {
                    // 获取参数方法
                    Map<String, Object> paramMap = RequestDataHelper.getRequestData();
                    if (CollectionUtils.isNotEmpty(paramMap)) {
                        paramMap.forEach((k, v) -> log.debug("{} : {}", k, v));
                        // 拼接表名
                        if (paramMap.containsKey("table_name")) {
                            tableName = tableName + RequestDataHelper.<String>getRequestData("table_name");
                        }
                    }
                    // 处理后的表名称
                    return tableName;
                }
        ));
        // 插件配置
        return interceptor;
    }

}