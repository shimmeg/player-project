package org.shimmeg.services.player;

import org.shimmeg.model.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStoreManager {

    private final Map<Integer, Player> playersStorage = new ConcurrentHashMap<>();

    public Player getPlayerById(int playerId) {
        return playersStorage.get(playerId);
    }

    public boolean addNewPlayer(Player player) {
        Player result = playersStorage.putIfAbsent(player.getPlayerId(), player);
        return result == null;
    }
}
