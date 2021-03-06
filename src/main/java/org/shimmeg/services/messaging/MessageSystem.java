package org.shimmeg.services.messaging;

import org.shimmeg.model.messaging.Message;
import org.shimmeg.model.Player;

public interface MessageSystem {
    void sendMessage(Message msg);
    Message getLastMessageForPlayer(Player player);
    void sendStopMessage(int playerId);
    void finishCommunication();
}
