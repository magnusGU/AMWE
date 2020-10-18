package com.example.amwe.Model.Messaging;

import java.math.BigInteger;

/**
 * Author: William
 *
 * Private key for cryptography; a data container containing the information needed for decryption.
 * Intended to always have a corresponding public key.
 * Private key should, as the name suggest, be private so only the receiver having the key can decrypt the messages encrypted for them.
 */
public class PrivateKey {

    BigInteger decryptingBigInt;
    BigInteger n;

    /**
     * Constructor
     * @param decryptingBigInt - a BigInteger used for decryption of what corresponding public key encrypted.
     * @param n - a BigInteger that is the same as in the corresponding public key.
     */
    public PrivateKey(BigInteger decryptingBigInt, BigInteger n) {
        this.decryptingBigInt = decryptingBigInt;
        this.n = n;
    }

}
