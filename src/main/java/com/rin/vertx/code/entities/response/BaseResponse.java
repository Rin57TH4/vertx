package com.rin.vertx.code.entities.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duongittien
 */
@Getter
@Setter
public class BaseResponse {
    private int code;
    private String message;

    public BaseResponse() {
        this.code = -1;
        this.message = "";
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
