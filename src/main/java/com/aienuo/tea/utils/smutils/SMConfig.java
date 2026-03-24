package com.aienuo.tea.utils.smutils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * 国密算法基础配置类
 * 在类加载时注册 BouncyCastle 安全提供者
 */
public class SMConfig {

    static {
        // 注册 BouncyCastle 安全提供者，只需注册一次[citation:4][citation:10]
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * SM4算法名称
     */
    public static final String SM4_ALGORITHM = "SM4";

    /**
     * SM3算法名称
     */
    public static final String SM3_ALGORITHM = "SM3";

    /**
     * SM2算法名称
     */
    public static final String SM2_ALGORITHM = "SM2";

    /**
     * SM4/CBC/PKCS7Padding 模式（需要BC提供者支持）
     */
    public static final String SM4_CBC_PKCS7_PADDING = "SM4/CBC/PKCS7Padding";

    /**
     * SM4/ECB/PKCS7Padding 模式
     */
    public static final String SM4_ECB_PKCS7_PADDING = "SM4/ECB/PKCS7Padding";

}