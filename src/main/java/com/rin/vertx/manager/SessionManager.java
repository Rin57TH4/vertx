package com.rin.vertx.manager;

import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.common.Common;
import com.rin.vertx.common.utils.AppUtils;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duongittien
 */
public class SessionManager {
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    private static SessionManager instance = new SessionManager();

    public static SessionManager getInstance() {
        return instance;
    }

    public void init() {
    }

    public void addSession(String sessionId, Session session) {
        this.sessions.put(sessionId, session);
    }

    public void removeSession(String sessionId, int reason) {
        Session session = this.sessions.remove(sessionId);
        if (session != null) {
            userDisconnected(session, reason);
        } else {
            System.out.printf("removeSession but null: {}", sessionId);
        }
    }

    private void userDisconnected(Session session, int reason) {
        if (session == null) {
            return;
        }
        String username = AppUtils.getUsername(session);
        try {
            CloseWebSocketFrame frame;
            if (reason == Common.USER_DISCONNECT_KICK_OUT) {
                frame = new CloseWebSocketFrame(4991, "Disconnect by kick out");
            } else {
                frame = new CloseWebSocketFrame();
            }

            session.getChannel().writeAndFlush(frame);
            session.getChannel().close();
            session.setChannel(null);
        } catch (Exception e) {
            System.out.printf("err : {}", e);
        }
//        logger.info("User {} disconnected with reason: {} - {}", userId, reason, Common.getDisconnectedReason(reason));
//        GameRoomManager.getInstance().getRoomManager().onUserDisconnected(session.getUser(), reason);
    }

}
