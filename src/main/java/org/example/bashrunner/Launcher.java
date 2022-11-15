package org.example.bashrunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        String action = System.getProperty("action");
        try {
            Bash bash = new Bash();
            if ("deploy".equalsIgnoreCase(action)) {
                bash.deploy();
            } else if ("rollback".equalsIgnoreCase(action)) {
                bash.rollback();
            } else {
                LOGGER.error("action {} not recognised!", action);
                System.exit(1);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while running script", e);
            System.exit(1);
        }

    }
}
