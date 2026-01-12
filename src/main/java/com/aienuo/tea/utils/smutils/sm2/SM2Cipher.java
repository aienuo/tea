package com.aienuo.tea.utils.smutils.sm2;

import com.aienuo.tea.utils.smutils.*;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class SM2Cipher extends AbstractCipher implements Encrypt, Decrypt {

    /**
     * 公钥
     */
    private final byte[] publicKey;

    /**
     * 私钥
     */
    private final byte[] privateKey;

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            return new byte[0];
        }
        String hexUpperString = hexString.toUpperCase();
        int length = hexUpperString.length() / 2;
        char[] hexChars = hexUpperString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]) & 0xff);
        }
        return d;
    }

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        int ol = l << 1;
        char[] out = new char[ol];
        // two characters form the hex value.
        int j = 0;
        for (byte datum : data) {
            out[j] = toDigits[(0xF0 & datum) >>> 4];
            j++;
            out[j] = toDigits[0x0F & datum];
            j++;
        }
        return out;
    }

    private static String encodeHexString(byte[] data) {
        return encodeHexString(data, true);
    }

    private static String encodeHexString(byte[] data, boolean toLowerCase) {
        return encodeHexString(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    private static String encodeHexString(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    public SM2Cipher(final String publicKey, final String privateKey) {
        this.publicKey = hexStringToBytes(publicKey);
        this.privateKey = hexStringToBytes(privateKey);
    }

    /**
     * 生成公私钥对
     *
     * @return KeyModel
     */
    public static KeyModel generateKeyPair() {
        SM2 sm2 = SM2.instance();
        AsymmetricCipherKeyPair key = sm2.keyPairGenerator.generateKeyPair();
        ECPrivateKeyParameters privateKeyParameters = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters publicKeyParameters = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = privateKeyParameters.getD();
        ECPoint publicKey = publicKeyParameters.getQ();
        KeyModel keyModel = new KeyModel();
        keyModel.setPublicKey(encodeHexString(publicKey.getEncoded()));
        keyModel.setPrivateKey(encodeHexString(privateKey.toByteArray()));
        return keyModel;
    }

    /**
     * 加密
     *
     * @param data - 待加密数据
     * @return byte[] - 加密后数据
     */
    @Override
    public byte[] encrypt(byte[] data) {
        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);
        SMUtils cipher = new SMUtils();
        SM2 sm2 = SM2.instance();
        ECPoint userKey = sm2.curve.decodePoint(this.publicKey);
        ECPoint c1 = cipher.initEnc(sm2, userKey);
        cipher.encryptSM(source);
        byte[] c3 = new byte[32];
        cipher.doFinalSM(c3);
        String encryptStr = encodeHexString(c1.getEncoded()) + encodeHexString(source) + encodeHexString(c3);
        return hexStringToBytes(encryptStr);
    }

    /**
     * 解密
     *
     * @param encryptedData - 加密后数据
     * @return byte[] - 解密后数据
     */
    @Override
    public byte[] decrypt(byte[] encryptedData) {
        String data = encodeHexString(encryptedData);
        byte[] c1Bytes = hexStringToBytes(data.substring(0, 130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = hexStringToBytes(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = hexStringToBytes(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));
        SM2 sm2 = SM2.instance();
        BigInteger userD = new BigInteger(1, this.privateKey);
        ECPoint c1 = sm2.curve.decodePoint(c1Bytes);
        SMUtils cipher = new SMUtils();
        cipher.initDec(userD, c1);
        cipher.decryptSM(c2);
        cipher.doFinalSM(c3);
        return c2;
    }

    /**
     * 后端加密（提供允许前端解密的数据）
     *
     * @param info - 待加密数据
     * @return String - 加密后数据
     */
    public String encrypt2HexForJavascript(String info) {
        String encrypted = this.encrypt2Hex(info);
        return encrypted.substring(2).toLowerCase();
    }

    /**
     * 后端解密（将前端加密数据解密）
     *
     * @param data - 待解密数据
     * @return String - 解密后数据
     */
    public String decryptFromJavascript(String data) {
        String password = new String(this.decrypt("04" + data));
        byte[] decode = Base64.getDecoder().decode(password.getBytes());
        password = new String(decode, StandardCharsets.UTF_8);
        return password;
    }

}

