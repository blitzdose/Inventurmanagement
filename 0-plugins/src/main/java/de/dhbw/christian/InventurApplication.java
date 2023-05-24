package de.dhbw.christian;

import io.javalin.Javalin;


public class InventurApplication {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(8080);
    }
}