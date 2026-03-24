package com.aienuo.tea.utils.smutils;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

/**
 * SM2:国产RSA非对称加密，基于ECC，故其签名速度与秘钥生成速度都快于RSA
 * SM2算法是一个基于椭圆曲线的公钥密码算法，其安全性主要依赖于椭圆曲线离散对数问题的难度。与RSA算法相比，SM2算法在相同的安全强度下，所需的密钥长度更短，因此，在加密和签名速度上具有一定的优势。此外，SM2算法在设计时也考虑了多种攻击手段，并采用了相应的防护措施，从而确保了其在实际应用中的安全性。
 */
public class SM2Utils extends SMConfig {

    /**
     * 生成SM2密钥对
     *
     * @return KeyPair - 对象，包含公钥和私钥
     */
    public static KeyPair generateKeyPair() throws Exception {
        // 使用 BC 提供者生成SM2密钥对[citation:6]
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SMConfig.SM2_ALGORITHM, org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator.initialize(new ECGenParameterSpec("sm2p256v1"), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成SM2密钥对（返回十六进制格式）
     *
     * @return 包含私钥和公钥十六进制字符串的数组 [privateKeyHex, publicKeyHex]
     */
    public static String[] generateKeyPairHex() throws Exception {
        KeyPair keyPair = generateKeyPair();

        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

        String privateKeyHex = Hex.toHexString(privateKey.getD().toByteArray());

        // 公钥格式：04 + X坐标 + Y坐标（非压缩格式）
        ECPoint q = publicKey.getQ();
        String publicKeyHex = Hex.toHexString(q.getEncoded(false));

        return new String[]{privateKeyHex, publicKeyHex};
    }

    /**
     * 从私钥十六进制字符串恢复私钥参数
     */
    private static ECPrivateKeyParameters getPrivateKeyParam(final String privateKeyHex) {
        BigInteger privateKeyD = new BigInteger(privateKeyHex, 16);
        ECDomainParameters ecSpec = getECDomainParameters();
        return new ECPrivateKeyParameters(privateKeyD, ecSpec);
    }

    /**
     * 从公钥十六进制字符串恢复公钥参数
     * 公钥格式：04 + X坐标（32字节）+ Y坐标（32字节）
     */
    private static ECPublicKeyParameters getPublicKeyParam(final String publicKeyHex) {
        ECDomainParameters ecSpec = getECDomainParameters();
        ECPoint point = ecSpec.getCurve().decodePoint(Hex.decode(publicKeyHex));
        return new ECPublicKeyParameters(point, ecSpec);
    }

    /**
     * 获取SM2椭圆曲线参数
     */
    private static ECDomainParameters getECDomainParameters() {
        return new ECDomainParameters(GMNamedCurves.getByOID(GMObjectIdentifiers.sm2p256v1));
    }

    /**
     * SM2加密
     *
     * @param plaintext    明文
     * @param publicKeyHex 公钥十六进制字符串
     * @return 密文（C1C2C3格式，十六进制字符串）
     */
    public static String encrypt(final String plaintext, final String publicKeyHex) throws Exception {
        if (plaintext == null || publicKeyHex == null) {
            return null;
        }

        byte[] plaintextBytes = plaintext.getBytes();
        ECPublicKeyParameters publicKeyParam = getPublicKeyParam(publicKeyHex);

        // 国标使用C1C2C3模式
        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C2C3);
        engine.init(true, new ParametersWithRandom(publicKeyParam, new SecureRandom()));

        byte[] ciphertext = engine.processBlock(plaintextBytes, 0, plaintextBytes.length);
        return Hex.toHexString(ciphertext);
    }

    /**
     * SM2解密
     *
     * @param ciphertextHex 密文十六进制字符串
     * @param privateKeyHex 私钥十六进制字符串
     * @return 明文
     */
    public static String decrypt(final String ciphertextHex, final String privateKeyHex) throws Exception {
        if (ciphertextHex == null || privateKeyHex == null) {
            return null;
        }

        byte[] ciphertext = Hex.decode(ciphertextHex);
        ECPrivateKeyParameters privateKeyParam = getPrivateKeyParam(privateKeyHex);

        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C2C3);
        engine.init(false, privateKeyParam);

        byte[] plaintext = engine.processBlock(ciphertext, 0, ciphertext.length);
        return new String(plaintext);
    }

    /**
     * SM2签名
     *
     * @param data          待签名数据
     * @param privateKeyHex 私钥十六进制字符串
     * @param userId        用户ID（可选，默认使用SM2规范推荐的ID）
     * @return 签名结果（十六进制字符串）
     */
    public static String sign(final String data, final String privateKeyHex, final String userId) throws Exception {
        if (data == null || privateKeyHex == null) {
            return null;
        }

        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        ECPrivateKeyParameters privateKeyParam = getPrivateKeyParam(privateKeyHex);

        SM2Signer signer = new SM2Signer();
        ParametersWithRandom param = new ParametersWithRandom(privateKeyParam, new SecureRandom());

        // 1. 初始化签名器
        signer.init(true, param);

        // 2. 处理用户 ID（SM2 规范要求先输入用户 ID）
        byte[] userIdBytes = (userId == null || userId.isEmpty())
                ? "1234567812345678".getBytes(StandardCharsets.UTF_8)
                : userId.getBytes(StandardCharsets.UTF_8);
        signer.update(userIdBytes, 0, userIdBytes.length);

        // 3. 更新待签名数据
        signer.update(dataBytes, 0, dataBytes.length);

        // 4. 生成签名
        byte[] signature = signer.generateSignature();
        return Hex.toHexString(signature);
    }

    /**
     * SM2签名（使用默认用户ID）
     */
    public static String sign(final String data, final String privateKeyHex) throws Exception {
        return sign(data, privateKeyHex, "1234567812345678");
    }

    /**
     * SM2验签
     *
     * @param data         原始数据
     * @param signatureHex 签名十六进制字符串
     * @param publicKeyHex 公钥十六进制字符串
     * @param userId       用户ID（需与签名时一致）
     * @return 签名是否有效
     */
    public static boolean verify(final String data, final String signatureHex, final String publicKeyHex, final String userId) throws Exception {
        if (data == null || signatureHex == null || publicKeyHex == null) {
            return false;
        }

        byte[] dataBytes = data.getBytes();
        byte[] signature = Hex.decode(signatureHex);
        ECPublicKeyParameters publicKeyParam = getPublicKeyParam(publicKeyHex);

        SM2Signer signer = new SM2Signer();
        signer.init(false, publicKeyParam);

        // 验签时传入相同的用户 ID（顺序必须与签名时一致）
        byte[] userIdBytes = (userId == null || userId.isEmpty())
                ? "1234567812345678".getBytes(StandardCharsets.UTF_8)
                : userId.getBytes(StandardCharsets.UTF_8);
        signer.update(userIdBytes, 0, userIdBytes.length);
        signer.update(dataBytes, 0, dataBytes.length);

        return signer.verifySignature(signature);
    }

    /**
     * SM2验签（使用默认用户ID）
     */
    public static boolean verify(final String data, final String signatureHex, final String publicKeyHex) throws Exception {
        return verify(data, signatureHex, publicKeyHex, "1234567812345678");
    }

}