package com.example.amwe.Model.Messaging;

import java.math.BigInteger;

/**
 * Public key for cryptography, a data container containing the information needed for encryption.
 * Intended to always have a corresponding private key.
 * Public key should, as the name suggest, be public so senders can encrypt messages only the intended receiver can decrypt.
 *
 * @author William Hugo
 */
public class PublicKey {

    private BigInteger encryptingBigInt;
    private BigInteger n;

    /**
     * Constructor
     * @param encryptingBigInt  - A BigInteger used for encryption that cannot be used to decrypt what it has encrypted, corresponding private key got information needed for decryption.
     * @param n                 - A BigInteger that is the same as in the corresponding private key.
     */
    public PublicKey(BigInteger encryptingBigInt, BigInteger n) {
        this.encryptingBigInt = encryptingBigInt;
        this.n = n;
    }

    public BigInteger getEncryptingBigInt() {
        return encryptingBigInt;
    }

    public BigInteger getN() {
        return n;
    }
}
