package com.rin.vertx.code.network;

import com.rin.vertx.code.network.session.SessionType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.vertx.core.Vertx;

/**
 * Created by duongittien
 */
public class PacketWebSocketHandler extends PacketChannelHandler {

    public PacketWebSocketHandler(Vertx vertx) {
        super(vertx, SessionType.WEB_SOCKET);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        //HandshakeComplete
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            session.setChannel(ctx.channel());
            sendCRT();
            System.out.printf("HandshakeComplete");
        }
    }
}
