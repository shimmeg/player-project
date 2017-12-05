package org.shimmeg;

import org.shimmeg.model.Player;
import org.shimmeg.services.ServicesProvider;
import org.shimmeg.services.player.PlayerFactory;
import org.shimmeg.settings.AppSettings;

import java.util.Arrays;

import static org.shimmeg.settings.AppSettings.*;

public class App {
    public static void main(String[] args) {
        initializeAppConfig(args);

        PlayerFactory playerFactory = ServicesProvider.getPlayerFactory();
        Player p1 = playerFactory.createNewPlayer("Petya");
        Player p2 = playerFactory.createNewPlayer("Vasya");
        p1.sendMessage("Hello", p2);
        
    }

    private static void initializeAppConfig(String[] args) {
        if (args.length == 2 && ApplicationMode.DISTRIBUTED.getOption().equals(args[0])) {
            AppSettings.setApplicationMode(ApplicationMode.DISTRIBUTED);
            AppSettings.setPort(Integer.parseInt(args[1]));
            System.out.println("Application working in Distributed mode; port: " + AppSettings.getPort());
        } else {
            AppSettings.setApplicationMode(ApplicationMode.SINGLE_PROCESS);
            System.out.println("Application working in Single process mode");
        }
    }
}