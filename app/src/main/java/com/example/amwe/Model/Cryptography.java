package com.example.amwe.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Cryptography {

    /**
     * Encryption turning a string into an encrypted byte array using a public key.
     * @param string - the message which is going to be encrypted.
     * @param key - public key gotten from the user which the message is for.
     * @return - the message encrypted into a byte array.
     */
    public byte[] encrypt(String string, PublicKey key) {

        BigInteger stringAsBigInt = new BigInteger(string.getBytes());
        return stringAsBigInt.modPow(key.encryptingBigInt,key.n).toByteArray();

    }

    /**
     * Decryption turning a byte array into a decrypted string, identical to the string originally sent if corresponding keys were used for encrypting and decrypting, otherwise nonsensical.
     * @param encrypted - an encrypted message in the form of a byte array.
     * @param key - the receiver's private key.
     * @return - the message decrypted turned into a string.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String decrypt(byte[] encrypted, PrivateKey key) {

        byte[] decrypted = (new BigInteger(encrypted)).modPow(key.decryptingBigInt,key.n).toByteArray();
        return new String(decrypted, StandardCharsets.UTF_8);

    }

}
