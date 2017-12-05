package org.shimmeg.model;

import org.shimmeg.services.ServicesProvider;
import org.shimmeg.services.messaging.model.Message;

import static org.shimmeg.settings.AppSettings.*;

public class Player {

    private final int playerId;
    private final String login;
    private int sentMessagesCount = 0;
    private int receivedMessagesCount = 0;

    public Player(String login) {
        this.login = login;
        playerId = ServicesProvider.getPlayersIdGenerator().getNextId();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void sendMessage(String text, int receiverId) {
        Message msg = buildMessage(text, receiverId);
        sentMessagesCount += 1;
        ServicesProvider.getMessagingService().sendMessage(msg);
    }

    public void sendMessage(String text, Player player) {
        sendMessage(text, player.getPlayerId());
    }

    private Message buildMessage(String text, int receiverId) {
        return Message.newBuilder()
                .setReceiverId(receiverId)
                .setSenderId(this.getPlayerId())
                .setText(text)
                .build();
    }

    public void onNewMessageReceived() {
        Message message = ServicesProvider.getMessagingService().getLastMessageForPlayer(this);
        System.out.println("Player " + this.toString() + " received message: " + message.getText());

        receivedMessagesCount += 1;
        if (StopCommunicationCondition.isReached(receivedMessagesCount, sentMessagesCount)) {
            ServicesProvider.getMessagingService().sendStopMessage(message.getSenderId());
            ServicesProvider.getMessagingService().finishCommunication();
            return;
        }
        sendMessage(message.getText() + sentMessagesCount, message.getSenderId());
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
        return Integer.hashCode(playerId);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", login='" + login + '\'' +
                '}';
    }
}
