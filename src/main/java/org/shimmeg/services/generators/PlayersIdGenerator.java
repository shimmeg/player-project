package org.shimmeg.services.generators;

import org.shimmeg.model.Player;
import org.shimmeg.settings.AppSettings;

import java.util.concurrent.atomic.AtomicInteger;

import static org.shimmeg.settings.AppSettings.*;

public class PlayersIdGenerator implements IdGenerator {

    private final AtomicInteger currentId;

    public PlayersIdGenerator() {
        if (getApplicationMode().equals(ApplicationMode.DISTRIBUTED)) {
            currentId = new AtomicInteger(getPort());
        } else {
            currentId = new AtomicInteger();
        }
    }

    @Override
    public int getNextId() {
        return currentId.getAndIncrement();
    }
}
