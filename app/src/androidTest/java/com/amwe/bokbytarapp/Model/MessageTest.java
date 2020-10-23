package com.amwe.bokbytarapp.Model;

import com.amwe.bokbytarapp.Model.Messaging.CryptographyKeysCreator;
import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.IMessageFactory;
import com.amwe.bokbytarapp.Model.Messaging.PrivateKey;
import com.amwe.bokbytarapp.Model.Messaging.PublicKey;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageTest {
    @Test
    public void isMessageCorrectlyDecrypted(){
        String text = "This is a test";
        String senderId="1";
        String receiverID="2";
        String timeStamp = "2020-10-22";
        IMessage message= IMessageFactory.createIMessage(text,senderId,receiverID,timeStamp);
        CryptographyKeysCreator cryptKeys = new CryptographyKeysCreator();
        PublicKey publicKey = cryptKeys.makePublicKey();
        PrivateKey privateKey = cryptKeys.makePrivateKey();

        String encryptedText=message.encryptMessage(publicKey);

        IMessage encryptedMessage = IMessageFactory.createIMessage(encryptedText,senderId,receiverID,timeStamp);

        String decryptedMessage=encryptedMessage.decryptMessage(privateKey);

        assertEquals(text,decryptedMessage);
    }
}

