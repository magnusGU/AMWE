package com.example.amwe.Model.Messaging;

public class MessageFactory {
    public Message createMessage(String text,String senderId, String receiverId,String timeStamp){
        return new Message(text,senderId,receiverId,timeStamp);

    }
}
