package com.rin.vertx.handlers;

/**
 * Created by duongittien
 */
public interface IHandlerFactory {
    void addHandler(String var1, Class<?> var2);

    void addHandler(String var1, Object var2);

    void removeHandler(String var1);

    Object findHandler(String var1) throws InstantiationException, IllegalAccessException;

    void clearAll();
}
