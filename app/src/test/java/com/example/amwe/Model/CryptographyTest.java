package com.example.amwe.Model;

import com.example.amwe.Model.Messaging.Cryptography;
import com.example.amwe.Model.Messaging.CryptographyKeys;
import com.example.amwe.Model.Messaging.PrivateKey;
import com.example.amwe.Model.Messaging.PublicKey;

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
    public void differentKeysToEncryptAndDecryptDoesNotWork() {
        CryptographyKeys cryptKeys1 = new CryptographyKeys();
        PublicKey publicKey1 = cryptKeys1.makePublicKey();

        CryptographyKeys cryptKeys2 = new CryptographyKeys();
        PrivateKey privateKey2 = cryptKeys2.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey1);

        String decryptedString = crypt.decrypt(array, privateKey2);

        assertEquals(false,(decryptedString.equals(originalString)));
    }
}
