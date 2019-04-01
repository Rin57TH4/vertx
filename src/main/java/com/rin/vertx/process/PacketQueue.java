package com.rin.vertx.process;

import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.session.Session;
import io.vertx.core.Vertx;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duongittien
 */
@Getter
@Setter
public class PacketQueue {
    private Vertx vertx;
    private Session session;
    private Packet packet;

    public PacketQueue(Vertx vertx, Session session, Packet packet) {
        this.vertx = vertx;
        this.session = session;
        this.packet = packet;
    }

}
