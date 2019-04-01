package com.rin.vertx.code.network;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public interface NettyServer {
    static NettyServer createWebSocket(Vertx vertx) {
        return new NettyWebSocket(vertx);
    }

    NettyServer requestHandler(Handler<Future<Long>> handler);

    void listen(int port, String host, Handler<AsyncResult<Void>> listenHandler);

    void close();
}
