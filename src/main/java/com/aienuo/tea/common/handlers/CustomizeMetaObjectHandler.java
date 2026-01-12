package com.aienuo.tea.common.handlers;

import com.aienuo.tea.model.po.User;
import com.aienuo.tea.utils.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 插入更新填充
 */
@Slf4j
@Component
public class CustomizeMetaObjectHandler implements MetaObjectHandler {

    /**
     * 获取 用户名
     *
     * @return String - 用户名
     */
    private String getUserName() {
        User currentUser = SecurityFrameworkUtils.getLoginUser();
        String username = "default user name";
        if (currentUser != null) {
            username = currentUser.getUsername();
        }
        return username;
    }

    /**
     * 插入填充
     *
     * @param metaObject - 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "creatorBy", String.class, getUserName());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新填充
     *
     * @param metaObject - 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, "updaterBy", String.class, getUserName());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

}