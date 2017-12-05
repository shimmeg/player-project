package org.shimmeg.services.messaging.model;

import java.io.Serializable;

public class Message implements Serializable{
    private final int receiverId;
    private final int senderId;
    private final String text;

    private Message(int receiverId, int senderId, String text) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.text = text;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int receiverId;
        private int senderId;
        private String text;

        public Builder setReceiverId(int receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public Builder setSenderId(int senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Message build() {
            return new Message(receiverId, senderId, text);
        }
    }

}
