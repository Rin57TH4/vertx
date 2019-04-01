package com.rin.vertx.code.codec;

/**
 * Created by duongittien
 */
public class AbstractEntityByteArrayConverter<T extends AbstractEntityByteArrayConverter> implements EntityByteArrayConverter<T> {
    @Override
    public byte[] toByteArray() {
        String json = EntityUtil.gson.toJson(this);
        return json.getBytes();
    }

    @Override
    public T fromByteArray(byte[] bArr) {
        String json = new String(bArr);
        T obj = (T) EntityUtil.gson.fromJson(json, getClass());
        return obj;
    }
}
