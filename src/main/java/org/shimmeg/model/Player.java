package org.shimmeg.model;

import org.shimmeg.services.ServicesProvider;
import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.settings.AppSettings;

import java.io.Serializable;

import static org.shimmeg.settings.AppSettings.*;

public class Player implements Serializable{

    private final int playerId;
    private final String login;
    private int sentMessagesCount = 0;
    private int receivedMessagesCount = 0;

    public Player(String login) {
        this.login = login;
        playerId = ServicesProvider.getPlayersIdGenerator().getNextId();
    }

    public void sendMessage(String text, Player receiver) {
        Message msg = buildMessage(text, receiver);
        sentMessagesCount += 1;
        ServicesProvider.getMessagingService().sendMessage(msg);
    }

    private Message buildMessage(String text, Player receiver) {
        return Message.newBuilder()
                .setReceiver(receiver)
                .setSender(this)
                .setText(text)
                .build();
    }

    public void onNewMessageReceived() {
        Message message = ServicesProvider.getMessagingService().getLastMessageForPlayer(this);

        System.out.println("Player " + this.toString() + " received message: " + message.getText());

        receivedMessagesCount += 1;
        if (StopCommunicationCondition.isReached(receivedMessagesCount, sentMessagesCount)) return;

        sendMessage(message.getText() + sentMessagesCount, message.getSender());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return playerId == player.playerId;
    }

    @Override
    public int hashCode() {
        return (int) (playerId ^ (playerId >>> 32));
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", login='" + login + '\'' +
                '}';
    }
}
