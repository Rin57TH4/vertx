package com.rin.vertx.code.network.error;

/**
 * Created by duongittien
 */
public class ServerRuntimeException extends RuntimeException {
    public ServerRuntimeException() {
    }

    public ServerRuntimeException(String message) {
        super(message);
    }

    public ServerRuntimeException(Throwable t) {
        super(t);
    }
}
