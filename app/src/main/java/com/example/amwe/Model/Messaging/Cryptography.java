package com.example.amwe.Model.Messaging;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Class used for encryption and decryption according to RSA cryptography.
 * Objects of this class are created and used in code where cryptography needs to be used.
 *
 * @author William Hugo
 */
public class Cryptography {

    /**
     * Encryption turning a string into an encrypted byte array using a public key.
     * @param string    - The message which is going to be encrypted.
     * @param key       - Public key gotten from the user which the message is for.
     * @return          - The message encrypted into a byte array.
     */
    public byte[] encrypt(String string, PublicKey key) {

        BigInteger stringAsBigInt = new BigInteger(string.getBytes());
        return stringAsBigInt.modPow(key.getEncryptingBigInt(),key.getN()).toByteArray();

    }

    /**
     * Decryption turning a byte array into a decrypted string, identical to the string originally sent if corresponding keys were used for encrypting and decrypting, otherwise nonsensical.
     * @param encrypted - An encrypted message in the form of a byte array.
     * @param key       - The receiver's private key.
     * @return          - The message decrypted turned into a string.
     */
    public String decrypt(byte[] encrypted, PrivateKey key) {

        byte[] decrypted = (new BigInteger(encrypted)).modPow(key.getDecryptingBigInt(),key.getN()).toByteArray();
        return new String(decrypted, StandardCharsets.UTF_8);

    }

}
