package com.example.amwe.Model;

import java.math.BigInteger;

public class PrivateKey {

    BigInteger d;
    BigInteger n;

    public PrivateKey(BigInteger d, BigInteger n) {
        this.d = d;
        this.n = n;
    }

}
