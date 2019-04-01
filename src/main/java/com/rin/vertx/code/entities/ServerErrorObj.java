package com.rin.vertx.code.entities;

import com.rin.vertx.code.network.error.ServerError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by duongittien
 */
@Setter
@Getter
@ToString

public class ServerErrorObj {
    private int code;
    private String message;

    public ServerErrorObj(ServerError error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
