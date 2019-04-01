package com.rin.vertx.code.network.session;

/**
 * Created by duongittien
 */
public enum  SessionState {
    AUTHENTICATION_KEY(0), READY(1), AUTHORIZED(2), CLOSED(3), KILLED(4);

    private int state;

    SessionState(int state) {
        this.state = state;
    }

    public static int to(SessionState sessionState) {
        return sessionState.state;
    }

    public static SessionState from(int state) {
        switch (state) {
            case 0:
                return AUTHENTICATION_KEY;
            case 1:
                return READY;
            case 2:
                return AUTHORIZED;
            case 3:
                return CLOSED;
            default:
                return KILLED;
        }
    }
}
