package org.shimmeg.services.player;

import org.shimmeg.model.Player;
import org.shimmeg.services.ServicesProvider;

import javax.xml.ws.Service;

public class PlayerFactory {

    public Player createNewPlayer(String login) {
        Player newPlayer;
        synchronized (this) {
            newPlayer = new Player(login);
            ServicesProvider.getPlayerStoreManager().addNewPlayer(newPlayer);
        }
        return newPlayer;
    }
}
