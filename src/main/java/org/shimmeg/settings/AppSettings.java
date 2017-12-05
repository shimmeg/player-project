package org.shimmeg.settings;

import java.util.HashMap;
import java.util.Map;

public class AppSettings {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final Map<Setting, Object> settings = new HashMap<>();

    private AppSettings() {}

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static void setPort(int port) {
        settings.put(Setting.PORT, port);
    }

    public static int getPort() {
        Integer port = (Integer) settings.get(Setting.PORT);
        return port != null ? port : 4040;
    }

    public static void setApplicationMode(ApplicationMode mode) {
        settings.put(Setting.APP_MODE, mode);
    }

    public static ApplicationMode getApplicationMode() {
        ApplicationMode mode = (ApplicationMode) settings.get(Setting.APP_MODE);
        return mode != null ? mode : ApplicationMode.SINGLE_PROCESS;
    }


    public enum Setting {
        PORT, APP_MODE
    }

    public enum ApplicationMode {
        SINGLE_PROCESS("-s"), DISTRIBUTED("-d");

        private final String option;

        ApplicationMode(String s) {
            this.option = s;
        }

        public String getOption() {
            return option;
        }
    }

    public static class StopCommunicationCondition {
        private static final int MAX_MESSAGES = 10;

        public static boolean isReached(int receivedMessagesCount, int sentMessagesCount) {
            return receivedMessagesCount >= MAX_MESSAGES && sentMessagesCount >= MAX_MESSAGES;
        }
    }
}
