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
        initializeMessagingSystem();

        if (ApplicationMode.SINGLE_PROCESS.equals(AppSettings.getApplicationMode())) {
            PlayerFactory playerFactory = ServicesProvider.getPlayerFactory();
            Player p1 = playerFactory.createNewPlayer("Petya");
            Player p2 = playerFactory.createNewPlayer("Vasya");
            p1.sendMessage("Hello", p2);
        } else if (AppSettings.getPort() == 4040){
            //4040 - initiator
            PlayerFactory playerFactory = ServicesProvider.getPlayerFactory();
            Player p1 = playerFactory.createNewPlayer("Petya");
            p1.sendMessage("Hello", 4050);
        } else if (AppSettings.getPort() == 4050) {
            //4050 - receiver
            PlayerFactory playerFactory = ServicesProvider.getPlayerFactory();
            playerFactory.createNewPlayer("Petya");
        }
    }

    private static void initializeAppConfig(String[] args) {
        if (args.length == 2 && ApplicationMode.DISTRIBUTED.getOption().equals(args[0])) {
            AppSettings.setApplicationMode(ApplicationMode.DISTRIBUTED);
            AppSettings.setPort(Integer.parseInt(args[1]));
            System.out.println("Application is working in Distributed mode; port: " + AppSettings.getPort());
        } else {
            AppSettings.setApplicationMode(ApplicationMode.SINGLE_PROCESS);
            System.out.println("Application is working in Single process mode");
        }
    }

    private static void initializeMessagingSystem() {
        ServicesProvider.getMessagingService();
    }
}