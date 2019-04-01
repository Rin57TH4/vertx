package com.rin.vertx.code.codec;

/**
 * Created by duongittien
 */
public interface EntityByteConverter<T> {
    byte toByte();

    T fromByte(byte b);
}
