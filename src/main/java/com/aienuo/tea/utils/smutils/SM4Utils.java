package com.aienuo.tea.utils.smutils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * SM4 对称加密工具类（主要用于数据的加密和解密）
 * SM4是国产分组密码算法，分组长度128位，密钥长度128位
 */
public class SM4Utils extends SMConfig {

    /**
     * 生成SM4密钥（128位）
     *
     * @return 16字节密钥的十六进制字符串
     */
    public static String generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(SMConfig.SM4_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(128, new SecureRandom());
        SecretKey key = kg.generateKey();
        return Hex.toHexString(key.getEncoded());
    }

    /**
     * 生成随机IV（初始化向量）
     *
     * @return 16字节IV的十六进制字符串（SM4块大小为16字节）
     */
    public static String generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return Hex.toHexString(iv);
    }

    /**
     * SM4/CBC/PKCS7Padding 加密
     *
     * @param plaintext 明文
     * @param keyHex    十六进制密钥（32字符，对应16字节）
     * @param ivHex     十六进制IV（32字符，对应16字节）
     * @return Base64编码的密文
     */
    public static String encryptCBC(String plaintext, String keyHex, String ivHex) throws Exception {
        if (plaintext == null || keyHex == null || ivHex == null) {
            return null;
        }

        byte[] keyBytes = Hex.decode(keyHex);
        byte[] ivBytes = Hex.decode(ivHex);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SMConfig.SM4_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance(SMConfig.SM4_CBC_PKCS7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * SM4/CBC/PKCS7Padding 解密
     *
     * @param ciphertext Base64编码的密文
     * @param keyHex     十六进制密钥（32字符，对应16字节）
     * @param ivHex      十六进制IV（32字符，对应16字节）
     * @return 明文字符串
     */
    public static String decryptCBC(String ciphertext, String keyHex, String ivHex) throws Exception {
        if (ciphertext == null || keyHex == null || ivHex == null) {
            return null;
        }

        byte[] keyBytes = Hex.decode(keyHex);
        byte[] ivBytes = Hex.decode(ivHex);
        byte[] encrypted = java.util.Base64.getDecoder().decode(ciphertext);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SMConfig.SM4_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance(SMConfig.SM4_CBC_PKCS7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * SM4/ECB/PKCS7Padding 加密（无IV，不推荐用于多块数据）
     */
    public static String encryptECB(String plaintext, String keyHex) throws Exception {
        if (plaintext == null || keyHex == null) {
            return null;
        }

        byte[] keyBytes = Hex.decode(keyHex);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SMConfig.SM4_ALGORITHM);

        Cipher cipher = Cipher.getInstance(SMConfig.SM4_ECB_PKCS7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * SM4/ECB/PKCS7Padding 解密
     */
    public static String decryptECB(String ciphertext, String keyHex) throws Exception {
        if (ciphertext == null || keyHex == null) {
            return null;
        }

        byte[] keyBytes = Hex.decode(keyHex);
        byte[] encrypted = java.util.Base64.getDecoder().decode(ciphertext);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, SMConfig.SM4_ALGORITHM);

        Cipher cipher = Cipher.getInstance(SMConfig.SM4_ECB_PKCS7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
