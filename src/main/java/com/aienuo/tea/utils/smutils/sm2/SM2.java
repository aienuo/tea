package com.aienuo.tea.utils.smutils.sm2;

import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.Fp;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SM2 {

    private static final String[] param = {
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
            "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"};

    static SM2 instance() {
        return new SM2();
    }

    final ECCurve curve;

    public final ECKeyPairGenerator keyPairGenerator;

    private SM2() {
        BigInteger eccP = new BigInteger(param[0], 16);
        BigInteger eccA = new BigInteger(param[1], 16);
        BigInteger eccB = new BigInteger(param[2], 16);
        BigInteger eccN = new BigInteger(param[3], 16);
        BigInteger eccGX = new BigInteger(param[4], 16);
        BigInteger eccGY = new BigInteger(param[5], 16);

        ECFieldElement xFieldElement = new Fp(eccP, eccGX);
        ECFieldElement yFieldElement = new Fp(eccP, eccGY);

        this.curve = new ECCurve.Fp(eccP, eccA, eccB);
        ECPoint eccPointG = new ECPoint.Fp(this.curve, xFieldElement, yFieldElement, false);

        ECDomainParameters eccBcSpec = new ECDomainParameters(this.curve, eccPointG, eccN);

        ECKeyGenerationParameters keyGenerationParameters = new ECKeyGenerationParameters(eccBcSpec, new SecureRandom());

        this.keyPairGenerator = new ECKeyPairGenerator();
        this.keyPairGenerator.init(keyGenerationParameters);
    }

}