package com.rin.vertx.common.utils;

/**
 * Created by duongittien
 */
public class ByteUtils {
    public static byte int3(int x) {
        return (byte) (x >> 24);
    }

    public static byte int2(int x) {
        return (byte) (x >> 16);
    }

    public static byte int1(int x) {
        return (byte) (x >> 8);
    }

    public static byte int0(int x) {
        return (byte) (x >> 0);
    }
}
