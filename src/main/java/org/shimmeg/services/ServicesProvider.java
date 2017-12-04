package org.shimmeg.services;

import org.shimmeg.services.generators.IdGenerator;
import org.shimmeg.services.generators.PlayersIdGenerator;
import org.shimmeg.services.messaging.MessageSystem;
import org.shimmeg.services.messaging.MessageSystemImpl;

public class ServicesProvider {

    private static final PlayersIdGenerator PLAYERS_ID_GENERATOR = new PlayersIdGenerator();
    private static final MessageSystemImpl MESSAGE_SYSTEM = new MessageSystemImpl();

    private ServicesProvider(){};

    public static MessageSystem getMessagingService() {
        return MESSAGE_SYSTEM;
    }

    public static IdGenerator getPlayersIdGenerator() {
        return PLAYERS_ID_GENERATOR;
    }
}
