package com.aienuo.tea.utils.smutils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;

/**
 * SM3 哈希算法工具类（用于数字签名和消息认证等场景）
 * SM3是国产密码杂凑算法，输出256位哈希值，安全性与SHA-256相当[citation:3]
 */
public class SM3Utils extends SMConfig {

    /**
     * 方式一：使用BouncyCastle轻量级API计算SM3哈希
     *
     * @param input 输入数据
     * @return 32字节的哈希值（十六进制字符串）
     */
    public static String hash(final String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        return hash(input.getBytes());
    }

    /**
     * 方式一：使用BouncyCastle轻量级API计算SM3哈希
     *
     * @param input 输入字节数组
     * @return 32字节的哈希值（十六进制字符串）
     */
    public static String hash(final byte[] input) {
        if (input == null || input.length == 0) {
            return null;
        }

        SM3Digest digest = new SM3Digest();
        digest.update(input, 0, input.length);

        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);

        return Hex.toHexString(result);
    }

    /**
     * 方式二：使用JCE标准API + BouncyCastle提供者
     * 需要先注册BouncyCastleProvider[citation:10]
     */
    public static String hashWithJCE(final String input) throws Exception {
        if (input == null || input.isEmpty()) {
            return null;
        }

        MessageDigest md = MessageDigest.getInstance(SMConfig.SM3_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        byte[] result = md.digest(input.getBytes());

        return Hex.toHexString(result);
    }

    /**
     * 验证数据完整性
     *
     * @param data         原始数据
     * @param expectedHash 期望的哈希值
     * @return 是否匹配
     */
    public static boolean verify(final String data, final String expectedHash) {
        if (data == null || expectedHash == null) {
            return false;
        }
        String actualHash = hash(data);
        return actualHash != null && actualHash.equalsIgnoreCase(expectedHash);
    }

}