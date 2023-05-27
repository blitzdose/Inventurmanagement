package de.dhbw.christian;

import de.dhbw.christian.javalin.JavalinWebServer;


public class InventurApplication {

    public static void main(String[] args) {

        JavalinWebServer javalinWebServer = new JavalinWebServer(args.length >= 1 ? Integer.parseInt(args[0]) : 8080);
        javalinWebServer.start();
    }
}