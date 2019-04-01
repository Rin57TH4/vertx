package com.rin.vertx.manager;

import com.rin.vertx.code.codec.EntityByteArrayConverter;
import com.rin.vertx.code.entities.CommandFlag;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.network.error.ServerRuntimeException;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.handlers.IClientRequestHandler;
import com.rin.vertx.handlers.IHandlerFactory;
import com.rin.vertx.handlers.ServerHandlerFactory;

import java.util.List;

public abstract class BaseServer {
    //    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IHandlerFactory handlerFactory = new ServerHandlerFactory(this);

    public abstract void init();

    public void destroy() {
        this.handlerFactory.clearAll();
    }

    public void addRequestHandler(String requestId, Class<?> theClass) {
        if (!IClientRequestHandler.class.isAssignableFrom(theClass)) {
            throw new ServerRuntimeException(String.format("Provided Request Handler does not implement IClientRequestHandler: %s, Cmd: %s", theClass, requestId));
        } else {
            this.handlerFactory.addHandler(requestId, theClass);
        }
    }

    public void handleClientRequest(String requestId, Session sender, Packet params) {
        System.out.printf("handleClientRequest");
        try {
            IClientRequestHandler handler = (IClientRequestHandler) this.handlerFactory.findHandler(requestId);
            if (handler == null) {
                throw new ServerRuntimeException("Request handler not found: '" + requestId + "'. Make sure the handler is registered in your extension using addRequestHandler()");
            }
            handler.handleClientRequest(sender, params);
        } catch (InstantiationException var6) {
            System.out.printf("Cannot instantiate handler class: " + var6);
//            logger.error("Cannot instantiate handler class: " + var6);
        } catch (IllegalAccessException var7) {
            System.out.printf("Illegal access for handler class: " + var7);
//            logger.error("Illegal access for handler class: " + var7);
        }
    }

    public void send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter data, Session recipient) {
        recipient.send(flag, code, type, senderId, data);
    }

    public void send(Packet packet, Session recipient) {
        recipient.send(packet);
    }

    public void send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter data, List<Session> recipients) {
        for (Session recipient : recipients) {
            send(flag, code, type, senderId, data, recipient);
        }
    }

    public void send(Packet packet, List<Session> recipients) {
        for (Session recipient : recipients) {
            send(packet, recipient);
        }
    }


}
