package com.aienuo.tea.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 缓存配置
 */
@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * Key 序列化器
     *
     * @return RedisSerializer<String>
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * Value 序列化器 - 配置安全白名单防止反序列化漏洞
     *
     * @return RedisSerializer<Object>
     */
    private RedisSerializer<Object> valueSerializer() {
        // 创建安全的 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // 配置多态类型验证器（白名单机制）- 核心安全措施
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                // 允许基础类型
                .allowIfSubType("java.lang")
                .allowIfSubType("java.util")
                .allowIfSubType("java.math")
                .allowIfSubType("java.time")
                // 允许 Spring 相关类
                .allowIfSubType("org.springframework.data.redis")
                // 允许项目业务类
                .allowIfSubType("com.aienuo.tea.model")
                .allowIfSubType("com.aienuo.tea.common")
                .allowIfSubType("com.aienuo.tea.business")
                .allowIfSubType("com.aienuo.tea.controller")
                .allowIfSubType("com.aienuo.tea.service")
                .allowIfSubType("com.aienuo.tea.mapper")
                // 允许 MapStruct 生成的类
                .allowIfSubType("com.aienuo.tea.model.converter")
                // 禁止其他所有类（防止恶意类反序列化）
                .build();

        // 应用类型验证器到 ObjectMapper
        objectMapper.activateDefaultTyping(
                typeValidator,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        // 如果使用了 Spring Security，添加这行
        objectMapper.registerModules(
                org.springframework.security.jackson2.SecurityJackson2Modules.getModules(
                        Thread.currentThread().getContextClassLoader()
                )
        );

        // 其他安全配置
        // 忽略未知字段（避免因为字段不匹配导致错误）
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 指定要序列化的域
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 创建并返回配置好的序列化器
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * RedisTemplate
     *
     * @param redisConnectionFactory - RedisConnectionFactory
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());

        return redisTemplate;
    }

    /**
     * 缓存管理器
     *
     * @param redisConnectionFactory - RedisConnectionFactory
     * @return CacheManager
     */
    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                // 设置默认缓存超时时间 30分钟
                .entryTtl(Duration.ofMinutes(30L))
                // 设置空值不缓存
                .disableCachingNullValues()
                // 设置 Key 序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                // 设置 Value 序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

}
