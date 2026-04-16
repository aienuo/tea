package com.aienuo.tea.common.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;

/**
 * 多数据源动态切换数据库方言分页拦截器
 */
@Slf4j
public class AutoPaginationInnerInterceptor extends PaginationInnerInterceptor {

    @Override
    protected IDialect findIDialect(Executor executor) {
        // 1. 首先尝试获取已经设置的方言（如果有的话）
        IDialect dialect = super.findIDialect(executor);
        // 2. 如果方言未设置，或者需要动态切换，则根据当前数据库连接类型动态获取
        if (dialect == null) {
            // 自动识别当前数据库连接类型是 MySQL、Oracle、DM、PG 还是其他
            DbType dbType = JdbcUtils.getDbType(executor);
            log.info("自动识别当前数据库连接类型是 {}", dbType);
            return DialectFactory.getDialect(dbType);
        }
        return dialect;
    }

}