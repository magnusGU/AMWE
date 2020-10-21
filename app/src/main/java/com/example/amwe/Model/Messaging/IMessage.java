package com.example.amwe.Model.Messaging;

public interface IMessage {
    String encodeMessage(PublicKey publicKey);
    String getMessage();
    String getSenderId();
    String getReceiverId();
    String getTimeStamp();
}
