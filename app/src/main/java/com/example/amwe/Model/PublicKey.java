package com.example.amwe.Model;

import java.math.BigInteger;

public class PublicKey {

    BigInteger encryptingBigInt;
    BigInteger n;

    public PublicKey(BigInteger encryptingBigInt, BigInteger n) {
        this.encryptingBigInt = encryptingBigInt;
        this.n = n;
    }

}
