package com.example.amwe.Model;

import java.math.BigInteger;

public class PublicKey {

    BigInteger e;
    BigInteger n;

    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

}
