package com.example.amwe.Model.Messaging;

import java.math.BigInteger;
import java.util.Random;

/**
 * A class which generates variables used in keys needed for RSA cryptography when created.
 * Used to create corresponding private & public keys to be used for encryption and decryption.
 *
 * @author William Hugo
 */
public class CryptographyKeys {

    private Random random;
    private BigInteger prime1;
    private BigInteger prime2;
    private BigInteger primesMultiplied;
    private BigInteger phi;
    private BigInteger encryptingBigInt;
    private BigInteger decryptingBigInt;

    /**
     * Constructor
     * Randomizes variables needed for RSA cryptography,
     * These variables later get put into corresponding public & private keys for functional encryption & decryption.
     */
    public CryptographyKeys() {
        random = new Random();
        prime1 = BigInteger.probablePrime(1000,random);
        prime2 = BigInteger.probablePrime(1000,random);
        primesMultiplied = prime1.multiply(prime2);
        phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        encryptingBigInt = BigInteger.probablePrime(1000,random);

        while (phi.gcd(encryptingBigInt).compareTo(BigInteger.ONE) > 0 && encryptingBigInt.compareTo(phi) < 0) {
            encryptingBigInt.add(BigInteger.ONE);
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
