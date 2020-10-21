package com.example.amwe.Model.Messaging;

import android.util.Base64;

public class Message {
    private String message;
    private String senderId;
    private String receiverId;
    private String timeStamp;

    public Message(String message,String senderId,String receiverId,String timeStamp){
        this.message=message;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.timeStamp=timeStamp;

    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }


    public String getReceiverId() {
        return receiverId;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    public String encodeMessage(PublicKey publicKey){
        Cryptography crypt = new Cryptography();
        String base64Message = Base64.encodeToString(crypt.encrypt(this.message,publicKey), Base64.DEFAULT);

       return base64Message;
    }
}
