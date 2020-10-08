package com.example.amwe.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CryptographyTest {
    @Test
    public void encryptingThenDecryptingWorks() {
        CryptographyKeys cryptKeys = new CryptographyKeys();
        PublicKey publicKey = cryptKeys.makePublicKey();
        PrivateKey privateKey = cryptKeys.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey);

        String decryptedString = crypt.decrypt(array, privateKey);

        assertEquals(true,(decryptedString.equals(originalString)));
    }

    @Test
    public void differentKeysToEncryptAndDecryptDoesntWork() {
        CryptographyKeys cryptKeys1 = new CryptographyKeys();
        PublicKey publicKey1 = cryptKeys1.makePublicKey();
        PrivateKey privateKey1 = cryptKeys1.makePrivateKey();

        CryptographyKeys cryptKeys2 = new CryptographyKeys();
        PublicKey publicKey2 = cryptKeys2.makePublicKey();
        PrivateKey privateKey2 = cryptKeys2.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey1);

        String decryptedString = crypt.decrypt(array, privateKey2);

        assertEquals(false,(decryptedString.equals(originalString)));
    }
}
