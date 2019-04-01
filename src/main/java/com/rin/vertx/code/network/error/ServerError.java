package com.rin.vertx.code.network.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by duongittien
 */
@Getter
@Setter
@ToString
public class ServerError extends Exception {
    public static final int INTERNAL = 1000;
    public static final int SERVER_AVAILABLE_ERROR = 1007;
    private int code;
    private String message;

    public ServerError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
