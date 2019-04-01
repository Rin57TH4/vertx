package com.rin.vertx;

import com.rin.vertx.code.VertxServerInterface;
import com.rin.vertx.handlers.DemoHandler;
import com.rin.vertx.manager.SessionManager;
import com.rin.vertx.process.ProcessMessageWorker;
import com.rin.vertx.verticle.WebSocketVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main implements VertxServerInterface {
    private static Main instance;
    private final Vertx vertx;
    public static boolean IS_MAINTAIN = false;

    public static Main getInstance() {
        return instance;
    }

    private Main() {
        instance = this;
        SessionManager.getInstance().init();
//        SystemRoomManager.getInstance().init();
        int numThreadRead = 5;
        int numThreadProcess = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreadProcess);

        for (int i = 0; i < numThreadRead; i++) {
            ProcessMessageWorker worker = new ProcessMessageWorker(executorService);
            Thread t = new Thread(worker, "Read-" + i);
            t.start();
        }

        VertxOptions options = new VertxOptions().setBlockedThreadCheckInterval(2000000);
        vertx = Vertx.vertx(options);
//        vertx.deployVerticle(new DemoHandler());
//        vertx.deployVerticle(new ResponseVerticle(this));
//        vertx.deployVerticle(new LoginVerticle(this), new DeploymentOptions().setWorker(true));
        vertx.deployVerticle(new WebSocketVerticle(), new DeploymentOptions().setWorker(true));
    }

    public static void main(String[] args) {
        new Main();

    }

    @Override
    public void onVertStart(String key) {
        System.out.printf("onVertStart{}", key);
//        logger.info("onVertStart: {}", key);
    }

    @Override
    public void onVertStop(String key) {
        System.out.printf("onVertStop: {}", key);
//                logger.info("onVertStop: {}", key);
    }

    public Vertx getVertX() {
        return vertx;
    }
}
