package com.rin.vertx.process;

import com.rin.vertx.api.ClientActionInterface;
import com.rin.vertx.code.codec.EntityUtil;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.ServerErrorObj;
import com.rin.vertx.code.entities.response.ServerResponse;
import com.rin.vertx.code.network.error.ServerError;
import com.rin.vertx.code.network.session.Session;

/**
 * Created by duongittien
 */
public class ClientRequestExecute<T, K> implements Runnable {
    //    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final T request;
    private final Session user;
    private final ClientActionInterface<T, K> func;
    private final Packet packet;

    public ClientRequestExecute(Session session, T request, Packet packet, ClientActionInterface<T, K> func) {
        this.request = request;
        this.user = session;
        this.packet = packet;
        this.func = func;

    }

    @Override
    public void run() {
        doExecute(this.user, this.request);
    }

    private void doExecute(Session session, T request) {
        try {
            K result = this.func.processRequest(session, request, packet);
            if (result != null) {
                packet.setContent(EntityUtil.toJson(ServerResponse.build(true, result)).getBytes());
                session.send(packet);
            }
        } catch (Throwable ex) {
            System.out.printf("Ex :{}", ex);
//            logger.error("Exception: ", ex);
            if (session == null || packet == null) {
                return;
            }
            ServerResponse response = ServerResponse.buildError(new ServerErrorObj(new ServerError(ServerError.SERVER_AVAILABLE_ERROR, "Hệ thống đang bận, vui lòng thử lại")));
            packet.setContent(EntityUtil.toJson(response).getBytes());
            session.send(packet);
        }
    }
}
