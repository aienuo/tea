package com.aienuo.tea.utils.smutils;

public interface Encrypt {

    byte[] encrypt(final String info);

    byte[] encrypt(final byte[] bytes);

    String encrypt2Hex(final String info);

    String encrypt2Hex(final byte[] bytes);

    String encrypt2B64(final String info);

    String encrypt2B64(final byte[] bytes);

}
