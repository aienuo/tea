package com.aienuo.tea.utils.smutils;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public abstract class AbstractEncrypt implements Encrypt {

    /**
     * 字节转字符串
     *
     * @param bytes 字节
     * @return String - 字符串
     */
    private static String getHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString().toUpperCase();
    }

    @Override
    public byte[] encrypt(String info) {
        byte[] bytes = info.getBytes(StandardCharsets.UTF_8);
        return encrypt(bytes);
    }

    @Override
    public String encrypt2Hex(String info) {
        byte[] encrypted = encrypt(info);
        return getHexString(encrypted);
    }

    @Override
    public String encrypt2Hex(byte[] bytes) {
        byte[] encrypted = encrypt(bytes);
        return getHexString(encrypted);
    }

    @Override
    public String encrypt2B64(String info) {
        byte[] encrypted = encrypt(info);
        return new String(Base64.encode(encrypted), StandardCharsets.UTF_8);
    }

    @Override
    public String encrypt2B64(byte[] bytes) {
        byte[] encrypted = encrypt(bytes);
        return new String(Base64.encode(encrypted), StandardCharsets.UTF_8);
    }

}
