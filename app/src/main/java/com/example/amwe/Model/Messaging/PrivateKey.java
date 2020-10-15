package com.example.amwe.Model.Messaging;

import java.math.BigInteger;

public class PrivateKey {

    BigInteger decryptingBigInt;
    BigInteger n;

    /**
     * Private key for cryptography; a data container containing the information needed for decryption. Should always have a corresponding public key.
     * @param decryptingBigInt - a BigInteger used for decryption of what corresponding public key encrypted.
     * @param n - a BigInteger that is the same as in the corresponding public key.
     */
    public PrivateKey(BigInteger decryptingBigInt, BigInteger n) {
        this.decryptingBigInt = decryptingBigInt;
        this.n = n;
    }

}
