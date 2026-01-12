package com.aienuo.tea.utils.smutils.sm3;

import com.aienuo.tea.utils.smutils.AbstractEncrypt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SM3Cipher extends AbstractEncrypt {

    private static final int BYTE_LENGTH = 32;

    private static final int BLOCK_LENGTH = 64;

    private static final int BUFFER_LENGTH = BLOCK_LENGTH * 1;

    private final byte[] xBuf = new byte[BUFFER_LENGTH];

    private int xBufOff;

    private byte[] vector = SM3.iv.clone();

    private int cntBlock = 0;

    public SM3Cipher() {
    }

    public SM3Cipher(SM3Cipher cipher) {
        System.arraycopy(cipher.xBuf, 0, this.xBuf, 0, cipher.xBuf.length);
        this.xBufOff = cipher.xBufOff;
        System.arraycopy(cipher.vector, 0, this.vector, 0, cipher.vector.length);
    }

    public int doFinal(byte[] out, int outOff) {
        byte[] tmp = doFinal();
        System.arraycopy(tmp, 0, out, outOff, tmp.length);
        return BYTE_LENGTH;
    }

    public void reset() {
        xBufOff = 0;
        cntBlock = 0;
        vector = SM3.iv.clone();
    }

    public void update(byte[] in, int inOff, int len) {
        int partLen = BUFFER_LENGTH - xBufOff;
        int inputLen = len;
        int dPos = inOff;
        if (partLen < inputLen) {
            System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
            inputLen -= partLen;
            if (Integer.MAX_VALUE - partLen > dPos) {
                dPos += partLen;
            }
            doUpdate();
            while (inputLen > BUFFER_LENGTH) {
                System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
                inputLen -= BUFFER_LENGTH;
                if (Integer.MAX_VALUE - BUFFER_LENGTH > dPos) {
                    dPos += BUFFER_LENGTH;
                }
                doUpdate();
            }
        }

        System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
        if (Integer.MAX_VALUE - inputLen > xBufOff) {
            xBufOff += inputLen;
        }
    }

    private void doUpdate() {
        byte[] b = new byte[BLOCK_LENGTH];
        for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH) {
            System.arraycopy(xBuf, i, b, 0, b.length);
            doHash(b);
        }
        xBufOff = 0;
    }

    private void doHash(byte[] n) {
        byte[] tmp = SM3.cf(vector, n);
        System.arraycopy(tmp, 0, vector, 0, vector.length);
        if (cntBlock < Integer.MAX_VALUE) {
            cntBlock++;
        }
    }

    private byte[] doFinal() {
        byte[] b = new byte[BLOCK_LENGTH];
        byte[] buffer = new byte[xBufOff];
        System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
        byte[] tmp = SM3.padding(buffer, cntBlock);
        for (int i = 0; i < tmp.length; i += BLOCK_LENGTH) {
            System.arraycopy(tmp, i, b, 0, b.length);
            doHash(b);
        }
        return vector;
    }

    public void update(byte in) {
        byte[] buffer = new byte[]{in};
        update(buffer, 0, 1);
    }

    public int getDigestSize() {
        return BYTE_LENGTH;
    }

    @Override
    public byte[] encrypt(byte[] bytes) {
        byte[] md = new byte[32];
        SM3Cipher sm3 = new SM3Cipher();
        sm3.update(bytes, 0, bytes.length);
        int sm3FinalInt = sm3.doFinal(md, 0);
        log.info("sm3 encrypt result: {}", sm3FinalInt);
        return md;
    }
}
