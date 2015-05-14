package com.my;

/**
 * Created by Sergey on 11.05.2015.
 */
public class Message {

    private String value;
    private String sender;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "value='" + value + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
