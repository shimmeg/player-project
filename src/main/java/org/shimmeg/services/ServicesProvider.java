package org.shimmeg.services;

import org.shimmeg.services.generators.IdGenerator;
import org.shimmeg.services.generators.PlayersIdGenerator;
import org.shimmeg.services.messaging.MessageSystem;
import org.shimmeg.services.messaging.SingleProcessMessageSystem;
import org.shimmeg.services.player.PlayerFactory;
import org.shimmeg.services.player.PlayerStoreManager;

public class ServicesProvider {

    private static final PlayersIdGenerator PLAYERS_ID_GENERATOR = new PlayersIdGenerator();
    private static final SingleProcessMessageSystem MESSAGE_SYSTEM = new SingleProcessMessageSystem();
    private static final PlayerStoreManager PLAYER_STORE_MANAGER = new PlayerStoreManager();
    private static final PlayerFactory PLAYER_FACTORY = new PlayerFactory();

    private ServicesProvider(){}

    public static MessageSystem getMessagingService() {
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
