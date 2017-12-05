package org.shimmeg.services.messaging;

import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SingleProcessMessageSystem extends AbstractMessageSystem {

    public SingleProcessMessageSystem() {
        System.out.println("Messageing Service - Single Process");
    }

    @Override
    public void sendMessage(Message msg) {
        putMessageToQueue(msg);
        notifyReceiver(msg.getReceiverId());
    }

    @Override
    public void sendStopMessage(int playerId) {
        System.out.println("Single process system stopping");
    }

    @Override
    public void finishCommunication() {

    }
}
