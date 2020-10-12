package com.example.amwe.Model;

import java.math.BigInteger;

public class PublicKey {

    BigInteger encryptingBigInt;
    BigInteger n;

    /**
     * Public key for cryptography, a data container containing the information needed for encryption. Should always have a corresponding private key.
     * @param encryptingBigInt - a BigInteger used for encryption that cannot be used to decrypt what it has encrypted, corresponding private key got information needed for decryption.
     * @param n - a BigInteger that is the same as in the corresponding private key.
     */
    public PublicKey(BigInteger encryptingBigInt, BigInteger n) {
        this.encryptingBigInt = encryptingBigInt;
        this.n = n;
    }

}
