package com.amwe.bokbytarapp.Model.Messaging;

import android.util.Base64;

/**
 * @author Elias Johansson
 * A class that represents the message stored in the database. It also has some behavoiur to
 * simplify encryption,decryption and interactions with the database.
 */

public class Message implements IMessage {
    private String text;
    private String senderId;
    private String receiverId;
    private String timeStamp;

    /**
     * @param text,       the text that will be sent or received.
     * @param senderId,   the unique id of the sender.
     * @param receiverId, the unique id of the receiver.
     * @param timeStamp,  the time the message was sent.
     */
    public Message(String text, String senderId, String receiverId, String timeStamp) {
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timeStamp = timeStamp;

    }

    public String getMessage() {
        return text;
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

    /**Decodes the message by calling using the Cryptography class.
     * @param publicKey, the public key of the user that will hold this message later on. Is used by encryption.
     * @return a Base64String, a string that will not destroy information about the encryption and at the same time
     * enables the database to handle it.
     */
    @Override
    public String encryptMessage(PublicKey publicKey) {
        Cryptography crypt = new Cryptography();
        String base64Message = Base64.encodeToString(crypt.encrypt(this.text, publicKey), Base64.DEFAULT);

        return base64Message;
    }

    /**Decodes the message by calling using the Cryptography class.
     * @param privateKey, the public key of the user that will hold this message later on. Is used to decrypt.
     * @return. a string that contains the decrypted text.
     */
    @Override
    public String decryptMessage(PrivateKey privateKey) {
        Cryptography crypt = new Cryptography();
        return crypt.decrypt(Base64.decode(this.text, Base64.DEFAULT), privateKey);
    }


}
