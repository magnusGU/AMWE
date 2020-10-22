package com.amwe.bokbytarapp.Model.Messaging;

/**
 * @author Elias Johansson
 * An interface that is commmon for all messages in the program, some not yet implemented.
 */
public interface IMessage {
    String encryptMessage(PublicKey publicKey);
    String decryptMessage(PrivateKey privateKey);
    String getMessage();
    String getSenderId();
    String getReceiverId();
    String getTimeStamp();
}
