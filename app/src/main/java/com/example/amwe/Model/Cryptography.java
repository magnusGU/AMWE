package com.example.amwe.Model;

import java.math.BigInteger;

public class Cryptography {

    public byte[] encrypt(String string, PublicKey key) {

        BigInteger stringAsBigInt = new BigInteger(string);
        return stringAsBigInt.modPow(key.encryptingBigInt,key.n).toByteArray();

    }

    public String decrypt(byte[] encrypted, PrivateKey key) {

        StringBuilder string = new StringBuilder();
        byte[] decrypted = (new BigInteger(encrypted)).modPow(key.decryptingBigInt,key.n).toByteArray();

        for (byte b: decrypted) {
            string.append(b);
        }

        return string.toString();

    }

}
