package com.aienuo.tea.utils.smutils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 公私钥对
 */
public class KeyModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
