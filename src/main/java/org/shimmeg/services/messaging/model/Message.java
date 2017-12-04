package org.shimmeg.services.messaging.model;

import org.shimmeg.model.Player;

public class Message {
    private final Player receiver;
    private final Player sender;
    private final String text;

    private Message(Player receiver, Player sender, String text) {
        this.receiver = receiver;
        this.sender = sender;
        this.text = text;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Player receiver;
        private Player sender;
        private String text;

        public Builder setReceiver(Player receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder setSender(Player sender) {
            this.sender = sender;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Message build() {
            return new Message(receiver, sender, text);
        }
    }

}
