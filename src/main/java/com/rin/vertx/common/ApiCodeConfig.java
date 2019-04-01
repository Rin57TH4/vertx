package com.rin.vertx.common;

import com.rin.vertx.common.utils.ByteUtils;

/**
 * Created by duongittien
 */
public class ApiCodeConfig {
    public static final String ROM = "rom";
    public static final String CHA = "cha";
    public static final String INF = "inf";
    public static final String SYS = "sys";
    public static final String CRT = "crt";
    public static final String LGI = "lgi";
    public static final String GAM = "gam";
    public static final String LOB = "lob";
    public static final String PIP = "pip"; // ping pong


    public enum Type {
        Type0(0),
        Type99(99);
        private byte b;

        Type(int param) {
            b = getByteFromInt(param);
        }

        Type(byte b) {
            this.b = b;
        }

        public byte getByte() {
            return b;
        }

        public int getType() {
            return ByteUtils.int0(b);
        }

        public static byte getByteFromInt(int param) {
            return (byte) (param & 0XFF);
        }

        public static byte getIntFromByte(byte param) {
            return ByteUtils.int0(param);
        }
    }
}
