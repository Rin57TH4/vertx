package com.rin.vertx.process;

import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.common.ApiCodeConfig;
import com.rin.vertx.manager.VertxServer;
import io.vertx.core.Vertx;

/**
 * Created by duongittien
 */
public class ExecuteMessageProcess implements Runnable {
    private final Vertx vertx;
    private final Session session;
    private final Packet packet;

    public ExecuteMessageProcess(Vertx vertx, Session session, Packet packet) {
        this.vertx = vertx;
        this.session = session;
        this.packet = packet;

    }

    @Override
    public void run() {
        session.preparePacketReceived(packet);
        String code = packet.getCode();
        int type = ApiCodeConfig.Type.getIntFromByte(packet.getType());
        try {
            if (session.shouldBlock(code, type)) {
                return;
            }
        } catch (Throwable ex) {
            System.out.printf("Exception{} ", ex);
            return;
        }
        String key = packet.getCode() + ApiCodeConfig.Type.getIntFromByte(packet.getType());

//        if (ApiCodeConfig.LGI.equals(packet.getCode()) && (type == LoginVerticle.REGISTER_COMMAND || type == LoginVerticle.WEB_TOKEN_LOGIN_COMMAND)) {
//            vertx.eventBus().send(LoginVerticle.LOGIN_KEY, new PacketEntity(session.getSessionId(), packet, session.getRemoteIp()));
//            return;
//        }

        try {
            VertxServer.getInstance().handleClientRequest(key, session, packet);
        } catch (Throwable ex) {
            System.out.printf("Dsđa Ex {}", ex);
//            logger.error("Exception: ", ex);
        }
    }
}
