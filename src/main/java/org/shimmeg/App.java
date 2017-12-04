package org.shimmeg;

import org.shimmeg.model.Player;
import org.shimmeg.settings.AppSettings;

import java.util.Arrays;

import static org.shimmeg.settings.AppSettings.*;

public class App {
    public static void main(String[] args) {
        initializeAppConfig(args);

        Player p1 = new Player("Petya");
        Player p2 = new Player("Vasya");
        p1.sendMessage("Hello", p2);

//        System.out.println("Application mode: " + AppSettings.getApplicationMode().toString() + "; port: " + AppSettings.getPort());
//        System.out.println(Arrays.deepToString(args));
    }

    private static void initializeAppConfig(String[] args) {
        if (ApplicationMode.DISTRIBUTED.getOption().equals(args[0])) {
            AppSettings.setApplicationMode(ApplicationMode.DISTRIBUTED);
            AppSettings.setPort(Integer.parseInt(args[1]));
            System.out.println("Application working in Distributed mode; port: " + AppSettings.getPort());
        } else {
            AppSettings.setApplicationMode(ApplicationMode.SINGLE_PROCESS);
            System.out.println("Application working in Single process mode");
        }
    }
}