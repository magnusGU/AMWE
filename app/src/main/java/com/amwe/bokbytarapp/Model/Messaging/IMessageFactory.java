package com.amwe.bokbytarapp.Model.Messaging;

/**
 * @author Elias Johansson
 *
 * Class that hides Message creation logic and works like a factory to some extent.
 */
public class IMessageFactory {
    /**
     * Class that hides creation logic for Message.
     *
     * @param text the String that contains the message of the Message
     * @param senderId the id of the sender.
     * @param receiverId the id of the receiver.
     * @param timeStamp the time the message was sent.
     * @return a new Message with the parameters above as parameters in it's constructor.
     */
    public static IMessage createIMessage(String text, String senderId, String receiverId, String timeStamp){
        return new TextMessage(text,senderId,receiverId,timeStamp);

    }
}
