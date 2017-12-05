package org.shimmeg.services;

import org.shimmeg.services.generators.IdGenerator;
import org.shimmeg.services.generators.PlayersIdGenerator;
import org.shimmeg.services.messaging.DistributedMessageSystem;
import org.shimmeg.services.messaging.MessageSystem;
import org.shimmeg.services.messaging.SingleProcessMessageSystem;
import org.shimmeg.services.player.PlayerFactory;
import org.shimmeg.services.player.PlayerStoreManager;
import org.shimmeg.settings.AppSettings;

import static org.shimmeg.settings.AppSettings.*;

public class ServicesProvider {

    private static final IdGenerator PLAYERS_ID_GENERATOR = new PlayersIdGenerator();
    private static MessageSystem MESSAGE_SYSTEM;
    private static final PlayerStoreManager PLAYER_STORE_MANAGER = new PlayerStoreManager();
    private static final PlayerFactory PLAYER_FACTORY = new PlayerFactory();

    private ServicesProvider(){}

    public static MessageSystem getMessagingService() {
        if (MESSAGE_SYSTEM == null) {
            if (getApplicationMode().equals(ApplicationMode.SINGLE_PROCESS)) {
                MESSAGE_SYSTEM = new SingleProcessMessageSystem();
            } else {
                DistributedMessageSystem distributedMessageSystem = new DistributedMessageSystem();
                distributedMessageSystem.start();
                MESSAGE_SYSTEM = distributedMessageSystem;
            }
        }
        return MESSAGE_SYSTEM;
    }

    public static IdGenerator getPlayersIdGenerator() {
        return PLAYERS_ID_GENERATOR;
    }

    public static PlayerStoreManager getPlayerStoreManager() {
        return PLAYER_STORE_MANAGER;
    }

    public static PlayerFactory getPlayerFactory() {
        return PLAYER_FACTORY;
    }

}
