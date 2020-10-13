package com.example.amwe.Model.Messaging;

public class Message {
    private String message;
    private String authorId;

    public Message(String message,String authorId){
        this.message=message;
        this.authorId=authorId;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthorId() {
        return authorId;
    }
}
