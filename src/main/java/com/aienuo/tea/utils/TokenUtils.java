package com.aienuo.tea.utils;

import com.aienuo.tea.utils.smutils.sm2.SM2Cipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class TokenUtils {

    /**
     * 获得4个长度的十六进制的 UUID
     *
     * @return 4个长度的十六进制的 UUID
     */
    public static String get4UUID() {
        return UUID.randomUUID().toString().split("-")[1];
    }

    /**
     * SM2 密码
     */
    private static final SM2Cipher sm2Cipher;

    static {
        // 公钥
        String publicKey = "04f2aaa7c70b5b77caddfe0d61991a0497c7a3f2408ad8ea100b3d91173b04def11e247471dcb0348a1daea9ddccaa30d0cb2822da13ec11810bc94c0ec078cf75";
        // 私钥
        String privateKey = "03ace564b89ae0f2ebf11a7b227c5fd960bf4a04d5d3ceeb964cc87f257460b6";
        // SM2 密码 Cipher
        sm2Cipher = new SM2Cipher(publicKey, privateKey);
    }

    /**
     * 后端加密（加密串在后端解密）
     *
     * @param data - 待加密数据
     * @return String - 加密后数据
     */
    public static String sm2Encrypt(final String data) {
        return sm2Cipher.encrypt2Hex(data);
    }

    /**
     * 后端解密（解后端加密过的串）
     *
     * @param data - 加密后数据
     * @return String - 解密后数据
     */
    public static String sm2Decrypt(final String data) {
        return new String(sm2Cipher.decrypt(data), StandardCharsets.UTF_8);
    }

    /**
     * 后端加密（前端解密）
     *
     * @param data - 待加密数据
     * @return String - 加密后数据
     */
    public static String sm2EncryptForJavaScript(final String data) {
        return sm2Cipher.encrypt2HexForJavascript(data);
    }

    /**
     * 后端解密（前端加密）
     *
     * @param data - 加密后数据
     * @return String - 解密后数据
     */
    public static String sm2DecryptFromJavaScript(final String data) {
        return sm2Cipher.decryptFromJavascript(data);
    }

    /**
     * 输出明文按 SHA-256 加密后的密文 (系统默认密码加密方式)
     *
     * @param password - 前端输入（接口入参）
     * @return String - 数据库存储密码
     */
    public static String encodePassword(final String password) {
        byte[] digest = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            log.info("前端输入（接口入参）：{}", password);
            log.info("加密对象：{}", messageDigest.toString());
            digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            log.info("NoSuchAlgorithmException:{}", exception.getMessage());
        }
        String data = new String(Base64.getEncoder().encode(digest));
        log.info("数据库存储密码：{}", data);
        return data;
    }

    /**
     * 构建单点登录 token
     *
     * @param username   - 用户名
     * @param clientType - pc/pad
     * @param domain     - 分级部署的域名
     * @return String - 单点 token
     */
    public static String getTokenByUsername(String domain, String clientType, String username) {
        try {
            String id = domain + ":" + clientType + "_" + username;
            return "TGT-" + sm2Encrypt(id) + "-" + get4UUID();
        } catch (Exception e) {
            log.error("生成 Token 异常： {}", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 单点登录 token 解析 用户名
     *
     * @param token - 单点登录 token
     * @return String - 用户名
     */
    public static String getUsernameByToken(String token) {
        try {
            String id = token.split("-")[1];
            String decrypt = sm2Decrypt(id);
            decrypt = StringUtils.replace(decrypt, "_", ":");
            return decrypt.split(":")[2];
        } catch (Exception e) {
            log.error("解析 用户名 异常： {}", e.getMessage(), e);
        }
        return "";
    }

}
