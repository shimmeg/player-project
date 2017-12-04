package org.shimmeg.services.messaging;

import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.model.Player;

public class DistributedMessageSystem implements MessageSystem {
    @Override
    public void sendMessage(Message msg) {

    }

    @Override
    public Message getLastMessageForPlayer(Player player) {
        return null;
    }
}
