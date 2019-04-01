package com.rin.vertx.verticle;

import com.rin.vertx.code.network.NettyServer;
import com.rin.vertx.handlers.DemoHandler;
import com.rin.vertx.manager.VertxServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

public class WebSocketVerticle extends AbstractVerticle {
    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    @Override
    public void start() throws Exception {
        super.start();

        NettyServer server = NettyServer.createWebSocket(vertx);
        server.requestHandler(time -> {
            time.complete(System.currentTimeMillis());
            System.out.println("Request web socket api done");

        });

        int wsPort = 55894;
        String host = "127.0.0.1";

        server.listen(wsPort, host, event -> {
            VertxServer.getInstance().addRequestHandler("dem0", DemoHandler.class);
        });
        System.out.println("Init Netty Web Socket Server !!!");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
