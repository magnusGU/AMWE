package com.example.amwe.Model.Messaging;

import java.math.BigInteger;
import java.util.Random;

public class CryptographyKeys {

    private Random random;
    private BigInteger prime1;
    private BigInteger prime2;
    private BigInteger primesMultiplied;
    private BigInteger phi;
    private BigInteger encryptingBigInt;
    private BigInteger decryptingBigInt;

    public CryptographyKeys() {
        random = new Random();
        prime1 = BigInteger.probablePrime(100,random);
        prime2 = BigInteger.probablePrime(100,random);
        primesMultiplied = prime1.multiply(prime2);
        phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        encryptingBigInt = BigInteger.probablePrime(100,random);

        while (phi.gcd(encryptingBigInt).compareTo(BigInteger.ONE) > 0 && encryptingBigInt.compareTo(phi) < 0) {
            encryptingBigInt.add(BigInteger.ONE);
        }

        decryptingBigInt = encryptingBigInt.modInverse(phi);
    }

    public PrivateKey makePrivateKey() {
        return new PrivateKey(decryptingBigInt, primesMultiplied);
    }

    public PublicKey makePublicKey() {
        return new PublicKey(encryptingBigInt, primesMultiplied);
    }

}
