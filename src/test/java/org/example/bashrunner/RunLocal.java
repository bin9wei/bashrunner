package org.example.bashrunner;

public class RunLocal {

    public static void main(String[] args) {
        System.setProperty("java.io.tmpdir", "./tmp");
        System.setProperty("action", "rollback");
        Launcher.start();
    }
}
