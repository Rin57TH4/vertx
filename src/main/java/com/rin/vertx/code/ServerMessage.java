package com.rin.vertx.code;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by duongittien
 */
@Getter
@Setter
@ToString
public class ServerMessage {
    private final String message;

    public ServerMessage(String message) {
        this.message = message;
    }
}
