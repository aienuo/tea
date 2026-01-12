package com.aienuo.tea.utils.smutils;

import com.aienuo.tea.utils.smutils.sm2.SM2;
import com.aienuo.tea.utils.smutils.sm3.SM3Cipher;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

@Slf4j
public class SMUtils {

    private int ct;

    private ECPoint p2;

    private SM3Cipher sm3keyBase;

    private SM3Cipher sm3c3;

    private final byte[] key;

    private byte keyOff;

    public SMUtils() {
        this.ct = 1;
        this.key = new byte[32];
        this.keyOff = 0;
    }

    private static byte[] byteConvert32Bytes(BigInteger n) {
        byte[] tmpd;
        if (n == null) {
            return new byte[0];
        }
        if (n.toByteArray().length == 33) {
            tmpd = new byte[32];
            System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
        } else if (n.toByteArray().length == 32) {
            tmpd = n.toByteArray();
        } else {
            tmpd = new byte[32];
            for (int i = 0; i < 32 - n.toByteArray().length; i++) {
                tmpd[i] = 0;
            }
            System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
        }
        return tmpd;
    }

    private void reset() {
        this.sm3keyBase = new SM3Cipher();
        this.sm3c3 = new SM3Cipher();

        byte[] p = byteConvert32Bytes(p2.getX().toBigInteger());
        this.sm3keyBase.update(p, 0, p.length);
        this.sm3c3.update(p, 0, p.length);

        p = byteConvert32Bytes(p2.getY().toBigInteger());
        this.sm3keyBase.update(p, 0, p.length);
        this.ct = 1;
        nextKey();
    }

    private void nextKey() {
        SM3Cipher sm3Cipher = new SM3Cipher(this.sm3keyBase);
        sm3Cipher.update((byte) (ct >> 24 & 0xff));
        sm3Cipher.update((byte) (ct >> 16 & 0xff));
        sm3Cipher.update((byte) (ct >> 8 & 0xff));
        sm3Cipher.update((byte) (ct & 0xff));
        int nextKey = sm3Cipher.doFinal(key, 0);
        log.info("sm3 next key result: {} ", nextKey);
        this.keyOff = 0;
        if (ct < Integer.MAX_VALUE) {
            this.ct++;
        }

    }

    public ECPoint initEnc(SM2 sm2, ECPoint userKey) {
        AsymmetricCipherKeyPair key = sm2.keyPairGenerator.generateKeyPair();
        ECPrivateKeyParameters privateKeyParameters = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters publicKeyParameters = (ECPublicKeyParameters) key.getPublic();
        BigInteger k = privateKeyParameters.getD();
        ECPoint c1 = publicKeyParameters.getQ();
        this.p2 = userKey.multiply(k);
        reset();
        return c1;
    }

    public void encryptSM(byte[] data) {
        this.sm3c3.update(data, 0, data.length);
        for (int i = 0; i < data.length; i++) {
            if (keyOff == key.length) {
                nextKey();
            }
            data[i] ^= key[keyOff++];
        }
    }

    public void initDec(BigInteger userD, ECPoint c1) {
        this.p2 = c1.multiply(userD);
        reset();
    }

    public void decryptSM(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (keyOff == key.length) {
                nextKey();
            }
            data[i] ^= key[keyOff++];
        }
        this.sm3c3.update(data, 0, data.length);
    }

    public void doFinalSM(byte[] c3) {
        byte[] p = byteConvert32Bytes(p2.getY().toBigInteger());
        this.sm3c3.update(p, 0, p.length);
        int sm3Final = this.sm3c3.doFinal(c3, 0);
        log.info("sm3 final result: {}", sm3Final);
        reset();
    }

}
