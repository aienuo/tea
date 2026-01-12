package com.aienuo.tea.common;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义 RedisKeyGenerator
 */
@Component("redisKeyGenerator")
public class RedisKeyGenerator implements KeyGenerator {

    /**
     * 生成 RedisKey
     *
     * @param target     - the target instance
     * @param method     - the method being called
     * @param paramArray - the method parameters (with any var-args expanded)
     * @return Object - RedisKey
     */
    @Override
    public Object generate(Object target, Method method, Object... paramArray) {
        StringBuilder key = new StringBuilder();
        for (Object param : paramArray) {
            if (param != null) {
                // 类型
                Class<?> clazz = param.getClass();
                // 遍历所有字段（包括父类）
                while (clazz != null && clazz != Object.class) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (!"serialVersionUID".equals(field.getName())) {
                            try {
                                field.setAccessible(true);
                                Object value = field.get(param);
                                // 只拼接 非 null 值
                                if (value != null) {
                                    key.append("&");
                                    key.append(field.getName());
                                    key.append("=");
                                    key.append(value);
                                }
                            } catch (IllegalAccessException e) {
                                // 忽略无法访问的字段
                            }
                        }
                    }
                    // 父类
                    clazz = clazz.getSuperclass();
                }
            }
        }
        return key.toString();
    }

}
