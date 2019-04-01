package com.rin.vertx.api;

import com.rin.vertx.code.codec.EntityUtil;
import com.rin.vertx.process.ClientRequestExecute;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.PacketImpl;
import com.rin.vertx.code.entities.response.ServerResponse;
import com.rin.vertx.code.network.error.ServerError;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.handlers.BaseClientRequestHandler;
import com.rin.vertx.process.ProcessManager;

/**
 * Created by duongittien
 */
public abstract class AbstractServerApi<T, K> extends BaseClientRequestHandler {
    public abstract String getCode();

    public abstract int getType();

    protected abstract Class<T> getRequestClass();

    @Override
    public void handleClientRequest(Session session, Packet data) {
        try {
            T request = getRequestClass() == String.class ? (T) new String(data.getContent()) : EntityUtil.gson.fromJson(new String(data.getContent()), getRequestClass());
            ClientActionInterface<T, K> instance = this.getFunc();
            ProcessManager.getInstance().enqueue(session, new ClientRequestExecute<>(session, request, data, instance), false);
        } catch (Throwable e) {
            handleError(session, e, data);
//            logger.error("Exception ", e);
        }
    }

    protected void handleError(Session session, Throwable serverError, Packet senderEntity) {
//        logger.error("serverError: {}", serverError);
        System.out.printf("serverError: {}", serverError);
        if (serverError instanceof ServerError) {
            System.out.printf("serverError code: {}",((ServerError) serverError).getCode());
//            logger.info("serverError code: {}", ((ServerError) serverError).getCode());
            if (((ServerError) serverError).getCode() == ServerError.INTERNAL) {
                return;
            }
            Packet packet = new PacketImpl(senderEntity, ServerResponse.build(false, serverError));
            session.send(packet);
        }
    }

    protected void response(Session session, Packet senderEntity, Object response) {
        System.out.printf("send response: {}", response);
        ServerResponse rs = ServerResponse.build(true, response);
        senderEntity.setContent(EntityUtil.toJson(rs).getBytes());
        send(senderEntity, session);
    }

    public abstract ClientActionInterface<T, K> getFunc();
}
