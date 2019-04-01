package com.rin.vertx.handlers;

import com.rin.vertx.code.codec.ant.Instantiation;
import com.rin.vertx.code.codec.ant.MultiHandler;
import com.rin.vertx.manager.BaseServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duongittien
 */
@Instantiation(Instantiation.InstantiationMode.NEW_INSTANCE)
public class ServerHandlerFactory implements IHandlerFactory {
    private final Map<String, Class<?>> handlers = new ConcurrentHashMap<>();
    private final Map<String, Object> cachedHandlers = new ConcurrentHashMap<>();
    private final BaseServer baseServer;

    public ServerHandlerFactory(BaseServer baseServer) {
        this.baseServer = baseServer;
    }

    @Override
    public void addHandler(String handlerKey, Class<?> handlerClass) {
        this.handlers.put(handlerKey, handlerClass);
    }

    @Override
    public void addHandler(String handlerKey, Object requestHandler) {
        this.setHandlerParentExtension(requestHandler);
        this.cachedHandlers.put(handlerKey, requestHandler);
    }

    @Override
    public synchronized void removeHandler(String handlerKey) {
        this.handlers.remove(handlerKey);
        if (this.cachedHandlers.containsKey(handlerKey)) {
            this.cachedHandlers.remove(handlerKey);
        }
    }

    @Override
    public Object findHandler(String key) throws InstantiationException, IllegalAccessException {
        Object handler = this.getHandlerInstance(key);
        if (handler == null) {
            int lastDotPos = key.lastIndexOf(".");
            if (lastDotPos > 0) {
                key = key.substring(0, lastDotPos);
            }
            handler = this.getHandlerInstance(key);
            if (handler != null && !handler.getClass().isAnnotationPresent(MultiHandler.class)) {
                handler = null;
            }
        }
        return handler;
    }

    @Override
    public synchronized void clearAll() {
        this.handlers.clear();
        this.cachedHandlers.clear();
    }

    private Object getHandlerInstance(String key) throws InstantiationException, IllegalAccessException {
        Object handler = this.cachedHandlers.get(key);
        if (handler != null)
            return handler;
        else {
            Class<?> handlerClass = this.handlers.get(key);
            if (handlerClass == null)
                return null;
            else {
                handler = handlerClass.newInstance();
                this.setHandlerParentExtension(handler);
                if (handlerClass.isAnnotationPresent(Instantiation.class)) {
                    Instantiation instAnnotation = handlerClass.getAnnotation(Instantiation.class);
                    if (instAnnotation.value() == Instantiation.InstantiationMode.SINGLE_INSTANCE) {
                        this.cachedHandlers.put(key, handler);
                    }
                }
                return handler;
            }
        }
    }

    private void setHandlerParentExtension(Object handler) {
        if (handler instanceof IClientRequestHandler) {
            ((IClientRequestHandler) handler).setServer(this.baseServer);
        }
    }
}
