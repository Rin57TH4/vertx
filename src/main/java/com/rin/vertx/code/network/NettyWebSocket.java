package com.rin.vertx.code.network;

import com.rin.vertx.code.codec.BinaryWebSocketDecoder;
import com.rin.vertx.code.codec.BinaryWebSocketEncoder;
import com.rin.vertx.code.codec.Decoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.VertxInternal;


public class NettyWebSocket implements NettyServer {
    private static final String WEBSOCKET_PATH = "/server";
    private final VertxInternal vertx;
    private Handler<Future<Long>> requestHandler;
    private ServerBootstrap bootstrap;
    private Channel channel;

    public NettyWebSocket(Vertx vertx) {
        this.vertx = (VertxInternal) vertx;
    }

    @Override
    public NettyServer requestHandler(Handler<Future<Long>> handler) {
        this.requestHandler = handler;
        return this;
    }

    @Override
    public void listen(int port, String host, Handler<AsyncResult<Void>> listenHandler) {
        if (requestHandler == null) {
            throw new IllegalStateException("No request handler set");
        }
        if (bootstrap != null) {
            throw new IllegalStateException("Already started");
        }
        ContextInternal context = vertx.getContext();

        EventLoop eventLoop = context.nettyEventLoop();
        EventLoopGroup acceptorGroup = vertx.getAcceptorEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(acceptorGroup, eventLoop);

        bootstrap.childHandler(new ChannelInitializer<io.netty.channel.Channel>() {
            @Override
            protected void initChannel(io.netty.channel.Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new BinaryWebSocketEncoder()); // Out
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast(new HttpObjectAggregator(65536));
                pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH)); // In
                pipeline.addLast(new BinaryWebSocketDecoder()); // In
                pipeline.addLast(new Decoder()); // In
                pipeline.addLast(new BinaryWebSocketEncoder()); // Out
                pipeline.addLast(new PacketWebSocketHandler(vertx)); // In
            }
        });

        ChannelFuture bindFuture = bootstrap.bind(host, port);
        bindFuture.addListener((ChannelFutureListener) future -> {
            context.executeFromIO(() -> {
                if (future.isSuccess()) {
                    channel = future.channel();
                    listenHandler.handle(Future.succeededFuture(null));
                } else {
                    listenHandler.handle(Future.failedFuture(future.cause()));
                }
            });
        });

    }

    @Override
    public void close() {
        if (channel != null) {
            channel.close();
            channel = null;
        }
    }
}
