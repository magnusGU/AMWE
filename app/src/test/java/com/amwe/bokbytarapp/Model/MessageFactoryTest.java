package com.amwe.bokbytarapp.Model;

import com.amwe.bokbytarapp.Model.Messaging.IMessage;
import com.amwe.bokbytarapp.Model.Messaging.MessageFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageFactoryTest {
    @Test
    public void isMessagedCreatedCorrectly(){
        String text = "This is a test";
        String senderId="1";
        String receiverID="2";
        String timeStamp = "2020-10-22";
        IMessage message = MessageFactory.createMessage(text,senderId,receiverID,timeStamp);

        assertEquals(text,message.getMessage());
        assertEquals(senderId,message.getSenderId());
        assertEquals(receiverID,message.getReceiverId());
        assertEquals(timeStamp,message.getTimeStamp());

    }
}
