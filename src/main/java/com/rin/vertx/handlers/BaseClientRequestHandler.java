package com.rin.vertx.handlers;

import com.rin.vertx.code.codec.EntityByteArrayConverter;
import com.rin.vertx.code.entities.CommandFlag;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.manager.BaseServer;

import java.util.List;

/**
 * Created by duongittien
 */
public abstract class BaseClientRequestHandler implements IClientRequestHandler {

    private BaseServer server;

    @Override
    public void setServer(BaseServer server) {
        this.server = server;
    }

    @Override
    public BaseServer getServer() {
        return this.server;
    }

    protected void send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter data, Session recipient) {
        this.server.send(flag, code, type, senderId, data, recipient);
    }

    protected void send(Packet packet, Session recipient) {
        this.server.send(packet, recipient);
    }

    protected void send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter data, List<Session> recipients) {
        this.server.send(flag, code, type, senderId, data, recipients);
    }

    protected void send(Packet packet, List<Session> recipients) {
        this.server.send(packet, recipients);
    }
}
