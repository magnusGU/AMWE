package com.amwe.bokbytarapp.Model.Messaging;

import java.math.BigInteger;

/**
 * Private key for cryptography; a data container containing the information needed for decryption.
 * Intended to always have a corresponding public key.
 * Private key should, as the name suggest, be private so only the receiver having the key can decrypt the messages encrypted for them.
 *
 * Used by:
 * Database, CryptographyKeysCreator, Cryptography, IMessage, TextMessage, ChatRoomAdapter, MessageListAdapter.
 *
 * @author William Hugo
 */
public class PrivateKey {

    private BigInteger decryptingBigInt;
    private BigInteger n;

    /**
     * Constructor
     * @param decryptingBigInt  - A BigInteger used for decryption of what corresponding public key encrypted.
     * @param n                 - A BigInteger that is the same as in the corresponding public key.
     */
    public PrivateKey(BigInteger decryptingBigInt, BigInteger n) {
        this.decryptingBigInt = decryptingBigInt;
        this.n = n;
    }

    public BigInteger getDecryptingBigInt() {
        return decryptingBigInt;
    }

    public BigInteger getN() {
        return n;
    }
}
