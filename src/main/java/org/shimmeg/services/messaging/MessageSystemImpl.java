package org.shimmeg.services.messaging;

import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystemImpl implements MessageSystem{
//    public static final MessageSystemImpl INSTANCE = new MessageSystemImpl();

    private Map<Player, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    @Override
    public void sendMessage(Message msg) {
        putMessageToQueue(msg);
        notifyReceiver(msg.getReceiver());
    }

    @Override
    public Message getLastMessageForPlayer(Player player) {
        ConcurrentLinkedQueue<Message> msgQueue = this.messages.get(player);
        return msgQueue == null ? null : msgQueue.poll();
    }

    private void putMessageToQueue(Message msg) {
        Player receiver = msg.getReceiver();
        messages.putIfAbsent(receiver, new ConcurrentLinkedQueue<>());
        messages.get(receiver).add(msg);
    }

    private void notifyReceiver(Player receiver) {
        receiver.onNewMessageReceived();
    }
}
