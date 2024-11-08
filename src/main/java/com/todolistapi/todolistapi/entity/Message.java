package com.todolistapi.todolistapi.entity;

public class Message {

    private String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public String getMessage() {
        return content;
    }

    public void setMessage(String content) {
        this.content = content;
    }
}
