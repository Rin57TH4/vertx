package com.rin.vertx.manager;


public class VertxServer extends BaseServer {
    private static VertxServer instance = new VertxServer();

    public static VertxServer getInstance() {
        if (instance == null)
            instance = new VertxServer();
        return instance;
    }

    private VertxServer() {
        init();
    }

    @Override
    public void init() {
        System.out.printf("VertxServer init !!!");
    }
}
