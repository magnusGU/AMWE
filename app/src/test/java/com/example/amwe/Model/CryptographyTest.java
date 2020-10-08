package com.example.amwe.Model;

import org.junit.Test;

import java.math.BigInteger;

public class CryptographyTest {
    @Test
    public void areKeysGenerated() {
        CryptographyKeys crypt = new CryptographyKeys();
        BigInteger e, d, n;
        e = crypt.getE();
        d = crypt.getD();
        n = crypt.getN();
        System.out.println(e.toString());
        System.out.println(d.toString());
        System.out.println(n.toString());
    }
}
