package com.rin.vertx.code.entities.response;

import com.google.gson.Gson;
import com.rin.vertx.code.codec.AbstractEntityByteArrayConverter;
import com.rin.vertx.code.codec.EntityUtil;
import com.rin.vertx.code.entities.ServerErrorObj;
import com.rin.vertx.code.network.error.ServerError;

/**
 * Created by duongittien
 */
public class ServerResponse extends AbstractEntityByteArrayConverter<ServerResponse> {
    protected boolean status;
    protected ServerErrorObj error;
    protected String data;

    public ServerResponse(boolean status) {
        super();
    }


    public <T> void build(Gson gson, T obj) {
        this.data = gson.toJson(obj);
    }

    public void setError(ServerErrorObj error) {
        this.error = error;
    }

    public static <T> ServerResponse build(boolean status, T obj) {
        if (!status) {
            ServerErrorObj errorObj = new ServerErrorObj((ServerError) obj);
            return buildError(errorObj);
        }
        return build(EntityUtil.gson, status, obj);
    }

    public static <T> ServerResponse build(Gson gson, boolean status, T obj) {
        ServerResponse response = new ServerResponse(status);
        response.build(gson, obj);
        return response;
    }


    public static ServerResponse buildError(ServerErrorObj obj) {
        ServerResponse response = new ServerResponse(false);
        response.setError(obj);
        return response;
    }
}
