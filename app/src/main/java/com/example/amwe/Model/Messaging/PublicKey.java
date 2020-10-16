package com.example.amwe.Model.Messaging;

import java.math.BigInteger;

/**
 * Author: William
 *
 * Public key for cryptography, a data container containing the information needed for encryption.
 * Intended to always have a corresponding private key.
 * Public key should, as the name suggest, be public so senders can encrypt messages only the intended receiver can decrypt.
 */
public class PublicKey {

    BigInteger encryptingBigInt;
    BigInteger n;

    /**
     * Constructor
     * @param encryptingBigInt - a BigInteger used for encryption that cannot be used to decrypt what it has encrypted, corresponding private key got information needed for decryption.
     * @param n - a BigInteger that is the same as in the corresponding private key.
     */
    public PublicKey(BigInteger encryptingBigInt, BigInteger n) {
        this.encryptingBigInt = encryptingBigInt;
        this.n = n;
    }

}
