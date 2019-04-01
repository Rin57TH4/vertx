package com.rin.vertx.api;

import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.session.Session;

/**
 * Created by duongittien
 */
public interface ClientActionInterface<T,K> {
    K processRequest(Session user, T request, Packet entity) throws Exception;
}
