package org.shimmeg.services.messaging;

import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SingleProcessMessageSystem extends AbstractMessageSystem{

    @Override
    public void sendMessage(Message msg) {
        putMessageToQueue(msg);
        notifyReceiver(msg.getReceiverId());
    }
}
