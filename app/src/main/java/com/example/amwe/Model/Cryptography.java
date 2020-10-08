package com.example.amwe.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Cryptography {

    public byte[] encrypt(String string, PublicKey key) {

        BigInteger stringAsBigInt = new BigInteger(string.getBytes());
        return stringAsBigInt.modPow(key.encryptingBigInt,key.n).toByteArray();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String decrypt(byte[] encrypted, PrivateKey key) {

        byte[] decrypted = (new BigInteger(encrypted)).modPow(key.decryptingBigInt,key.n).toByteArray();
        return new String(decrypted, StandardCharsets.UTF_8);

    }

}
