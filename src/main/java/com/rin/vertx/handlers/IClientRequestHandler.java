package com.rin.vertx.handlers;

import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.manager.BaseServer;

/**
 * Created by duongittien
 */
public interface IClientRequestHandler {
    void handleClientRequest(Session session, Packet packet);

    void setServer(BaseServer server);

    BaseServer getServer();
}
