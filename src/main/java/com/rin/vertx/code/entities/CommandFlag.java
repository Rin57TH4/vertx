package com.rin.vertx.code.entities;

import com.rin.vertx.code.codec.EntityByteConverter;
import com.rin.vertx.common.utils.ByteUtils;

/**
 * Created by duongittien
 */
public class CommandFlag implements EntityByteConverter {
    public static final byte FLAG_ACTIVE = 0x00;
    public static final byte FLAG_PASSIVE = 0x01;
    public static final byte FLAG_PING = 0x02;
    public static final byte FLAG_NO_CRYPTO = 0x00;
    public static final byte FLAG_XOR_CRYPTO = 0x01;
    public static final byte FLAG_NOCOMPRESS = 0x00;
    public static final byte FLAG_COMPRESS = 0x01;

    private byte cmdType;
    private byte crypt;
    private byte compress;

    public CommandFlag() {

    }

    public CommandFlag(byte cmdType, byte crypt, byte compress) {
        this.cmdType = cmdType;
        this.crypt = crypt;
        this.compress = compress;
    }

    public CommandFlag(int type, boolean bCrypt, boolean bCompress) {
        this.cmdType = ByteUtils.int3(type);
        if (bCrypt) {
            this.crypt = FLAG_XOR_CRYPTO;
        } else {
            this.crypt = FLAG_NO_CRYPTO;
        }
        if (bCompress) {
            this.compress = FLAG_COMPRESS;
        } else {
            this.compress = FLAG_NOCOMPRESS;
        }
    }

    @Override
    public byte toByte() {
        return (byte) (((compress << 5) & 0xf0) | ((crypt << 4) & 0xf0) | (cmdType & 0x0f));
    }

    @Override
    public CommandFlag fromByte(byte b) {
        cmdType = (byte) (b & 0x0f);
        crypt = (byte) (((b & 0xff) >> 4) & 1);
        compress = (byte) (((b & 0xff) >> 5) & 1);
        return this;
    }

    public static CommandFlag buildFromByte(byte b) {
        CommandFlag commandFlag = new CommandFlag();
        commandFlag.cmdType = (byte) (b & 0x0f);
        commandFlag.crypt = (byte) (((b & 0xff) >> 4) & 1);
        commandFlag.compress = (byte) (((b & 0xff) >> 5) & 1);
        return commandFlag;
    }

    public int getFlag() {
        return cmdType;
    }

    @Override
    public String toString() {
        return "CommandFlag { cmdType : " + cmdType + " crypt : " + crypt + " compress : " + compress + "}";
    }

    public boolean isCrypt() {
        return crypt == FLAG_XOR_CRYPTO;
    }

    public boolean isCompress() {
        return compress == FLAG_COMPRESS;
    }


}
