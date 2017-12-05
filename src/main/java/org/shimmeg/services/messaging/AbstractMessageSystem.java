package org.shimmeg.services.messaging;

import org.shimmeg.model.Player;
import org.shimmeg.services.ServicesProvider;
import org.shimmeg.services.messaging.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractMessageSystem implements MessageSystem {

    private Map<Integer, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    @Override
    public Message getLastMessageForPlayer(Player player) {
        ConcurrentLinkedQueue<Message> msgQueue = this.messages.get(player.getPlayerId());
        return msgQueue == null ? null : msgQueue.poll();
    }

    protected void putMessageToQueue(Message msg) {
        Integer receiverId = msg.getReceiverId();
        messages.putIfAbsent(receiverId, new ConcurrentLinkedQueue<>());
        messages.get(receiverId).add(msg);
    }

    protected void notifyReceiver(int receiverId) {
        Player receiver = ServicesProvider.getPlayerStoreManager().getPlayerById(receiverId);
        receiver.onNewMessageReceived();
    }
}
