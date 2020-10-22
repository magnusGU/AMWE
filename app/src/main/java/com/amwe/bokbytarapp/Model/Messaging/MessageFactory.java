package com.amwe.bokbytarapp.Model.Messaging;

public class MessageFactory {
    public IMessage createMessage(String text,String senderId, String receiverId,String timeStamp){
        return new Message(text,senderId,receiverId,timeStamp);

    }
}
