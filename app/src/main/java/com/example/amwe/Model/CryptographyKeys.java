package com.example.amwe.Model;

import java.math.BigInteger;
import java.util.Random;

public class CryptographyKeys {

    private Random random;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    public CryptographyKeys() {
        random = new Random();
        p = BigInteger.probablePrime(100,random);
        q = BigInteger.probablePrime(100,random);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(100,random);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getN() {
        return n;
    }

}
