package com.rin.vertx.code.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rin.vertx.code.codec.ant.AnnotationExclusionStrategy;
import com.rin.vertx.code.entities.Packet;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by duongittien
 */
public class EntityUtil {
    public static final Gson gson = new GsonBuilder()
            .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
            .addDeserializationExclusionStrategy(new AnnotationExclusionStrategy()).create();

    public static <T extends EntityByteArrayConverter<T>> T build(byte[] bData, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return t.fromByteArray(bData);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <T> T build(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <T> T build(Class<T> clazz, Object... objects) {
        try {
            Class[] objs = new Class[objects.length];
            int size = objects.length;
            for (int i = 0; i < size; i++) {
                objs[i] = objects[i].getClass();
            }
            Constructor<?> cons = clazz.getConstructor(objs);
            T t = (T) cons.newInstance(objects);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <T extends EntityByteArrayConverter<T>> T build(Packet packet, Class<T> clazz) {
        return build(packet.getContent(), clazz);
    }

    public static <T> T build(String rs, Class<T> clzz) {
        try {
            return gson.fromJson(rs, clzz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String s, Class<T> tClass) {
        return gson.fromJson(s, tClass);
    }

    public static <T> List<T> toList(String json) {
        if (null == json) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }
}
