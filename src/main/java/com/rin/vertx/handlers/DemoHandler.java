package com.rin.vertx.handlers;

import com.rin.vertx.api.AbstractServerApi;
import com.rin.vertx.api.ClientActionInterface;
import com.rin.vertx.code.codec.ant.Instantiation;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.response.BaseResponse;
import com.rin.vertx.code.network.session.Session;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class DemoHandler extends AbstractServerApi<String, Object> implements ClientActionInterface<String, Object> {
    @Override
    public String getCode() {
        return "code";
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    protected Class<String> getRequestClass() {
        return String.class;
    }

    @Override
    public ClientActionInterface<String, Object> getFunc() {
        return this;
    }

    @Override
    public Object processRequest(Session user, String request, Packet entity) throws Exception {
        System.out.printf(" DemoHandler processRequest");
        BaseResponse response = new BaseResponse();
        response.setCode(0);
        response.setMessage("OK");
        return response;
    }
}
