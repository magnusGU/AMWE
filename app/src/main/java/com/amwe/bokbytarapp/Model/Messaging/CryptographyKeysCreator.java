package com.amwe.bokbytarapp.Model.Messaging;

import java.math.BigInteger;
import java.util.Random;

/**
 * A class which generates variables used in keys needed for RSA cryptography when created.
 * Used to create corresponding private & public keys to be used for encryption and decryption.
 *
 * @author William Hugo
 */
public class CryptographyKeysCreator {

    private BigInteger primesMultiplied;
    private BigInteger encryptingBigInt;
    private BigInteger decryptingBigInt;

    /**
     * Constructor
     * Randomizes variables needed for RSA cryptography,
     * These variables later get put into corresponding public & private keys for functional encryption & decryption.
     */
    public CryptographyKeysCreator() {
        Random random = new Random();
        BigInteger prime1 = BigInteger.probablePrime(256, random);
        BigInteger prime2 = BigInteger.probablePrime(256, random);
        primesMultiplied = prime1.multiply(prime2);
        BigInteger phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        encryptingBigInt = BigInteger.probablePrime(256, random);

        while (phi.gcd(encryptingBigInt).compareTo(BigInteger.ONE) > 0 && encryptingBigInt.compareTo(phi) < 0) {
            encryptingBigInt = encryptingBigInt.add(BigInteger.ONE);
        }

        decryptingBigInt = encryptingBigInt.modInverse(phi);
    }

    /**
     * Creates a new private key that will be corresponding with any public key created by the same CryptographyKeys object.
     * @return private key created.
     */
    public PrivateKey makePrivateKey() {
        return new PrivateKey(decryptingBigInt, primesMultiplied);
    }

    /**
     * Creates a new public key that will be corresponding with any private key created by the same CryptographyKeys object.
     * @return public key created.
     */
    public PublicKey makePublicKey() {
        return new PublicKey(encryptingBigInt, primesMultiplied);
    }

}
