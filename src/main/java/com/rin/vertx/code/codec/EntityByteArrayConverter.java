package com.rin.vertx.code.codec;

/**
 * Created by duongittien
 */
public interface EntityByteArrayConverter<T> {
    byte[] toByteArray();

    T fromByteArray(byte[] bArr);
}
