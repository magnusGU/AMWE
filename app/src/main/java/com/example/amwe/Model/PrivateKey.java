package com.example.amwe.Model;

import java.math.BigInteger;

public class PrivateKey {

    BigInteger decryptingBigInt;
    BigInteger n;

    public PrivateKey(BigInteger decryptingBigInt, BigInteger n) {
        this.decryptingBigInt = decryptingBigInt;
        this.n = n;
    }

}
