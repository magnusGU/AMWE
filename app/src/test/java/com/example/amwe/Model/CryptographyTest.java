package com.example.amwe.Model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        assertTrue((decryptedString.equals(originalString)));
    }

    @Test
    public void differentKeysToEncryptAndDecryptDoesNotWork() {
        CryptographyKeys cryptKeys1 = new CryptographyKeys();
        PublicKey publicKey1 = cryptKeys1.makePublicKey();

        CryptographyKeys cryptKeys2 = new CryptographyKeys();
        PrivateKey privateKey2 = cryptKeys2.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey1);

        String decryptedString = crypt.decrypt(array, privateKey2);

        assertFalse((decryptedString.equals(originalString)));
    }
}
