package com.example.amwe.Model;

import com.example.amwe.Model.Messaging.Cryptography;
import com.example.amwe.Model.Messaging.CryptographyKeysCreator;
import com.example.amwe.Model.Messaging.PrivateKey;
import com.example.amwe.Model.Messaging.PublicKey;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CryptographyTest {
    @Test
    public void encryptingThenDecryptingWorks() {
        CryptographyKeysCreator cryptKeys = new CryptographyKeysCreator();
        PublicKey publicKey = cryptKeys.makePublicKey();
        PrivateKey privateKey = cryptKeys.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey);

        String decryptedString = crypt.decrypt(array, privateKey);

        assertEquals(decryptedString, originalString);
    }

    @Test
    public void differentKeysToEncryptAndDecryptDoesNotWork() {
        CryptographyKeysCreator cryptKeys1 = new CryptographyKeysCreator();
        PublicKey publicKey1 = cryptKeys1.makePublicKey();

        CryptographyKeysCreator cryptKeys2 = new CryptographyKeysCreator();
        PrivateKey privateKey2 = cryptKeys2.makePrivateKey();

        Cryptography crypt = new Cryptography();
        String originalString = "This is a test";
        byte[] array = crypt.encrypt(originalString, publicKey1);

        String decryptedString = crypt.decrypt(array, privateKey2);

        assertNotEquals(decryptedString, originalString);
    }
}
