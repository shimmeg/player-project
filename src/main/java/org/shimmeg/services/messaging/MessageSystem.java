package org.shimmeg.services.messaging;

import org.shimmeg.services.messaging.model.Message;
import org.shimmeg.model.Player;

public interface MessageSystem {
    void sendMessage(Message msg);
    Message getLastMessageForPlayer(Player player);
}
