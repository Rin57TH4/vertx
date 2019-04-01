package com.rin.vertx.code.entities;

import com.rin.vertx.code.codec.EntityUtil;
import com.rin.vertx.code.entities.response.ServerResponse;

/**
 * Created by duongittien
 */
public class PacketImpl implements Packet {
    private CommandFlag commandFlag;
    private int dataLength;
    private String code;
    private byte type;
    private int senderId;
    private byte[] content;

    public PacketImpl() {
    }

    public PacketImpl(CommandFlag commandFlag, int dataLength, String code, byte type, int senderId, byte[] content) {
        this.commandFlag = commandFlag;
        this.dataLength = dataLength;
        this.code = code;
        this.type = type;
        this.senderId = senderId;
        this.content = content;
    }

    public PacketImpl(CommandFlag flag, String code, byte type, int senderId, Object contentData) {
        this.commandFlag = flag;
        this.code = code;
        this.type = type;
        this.senderId = senderId;
        this.content = EntityUtil.gson.toJson(contentData).getBytes();
        this.dataLength = content.length;
    }

    public PacketImpl(Packet packet, Object contentData) {
        this.commandFlag = packet.getCommandFlag();
        this.code = packet.getCode();
        this.type = packet.getType();
        this.senderId = packet.getSenderId();
        this.content = EntityUtil.gson.toJson(contentData).getBytes();
        this.dataLength = content.length;
    }


    @Override
    public CommandFlag getCommandFlag() {
        return this.commandFlag;
    }

    @Override
    public void setCommandFlag(CommandFlag commandFlag) {
        this.commandFlag = commandFlag;
    }

    @Override
    public int getDataLength() {
        return this.dataLength;
    }

    @Override
    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public byte getType() {
        return this.type;
    }

    @Override
    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public int getSenderId() {
        return this.senderId;
    }

    @Override
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    @Override
    public int getFlag() {
        return this.commandFlag.getFlag();
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
        dataLength = content.length;
    }

    @Override
    public void setContent(Object obj) {
        if (obj instanceof String) {
            this.content = ((String) obj).getBytes();
        } else if (obj != null) {
            this.content = EntityUtil.toJson(obj).getBytes();
        }
        this.dataLength = content.length;
    }

    @Override
    public String toString() {
        return "Packet { code " + code + " flag " + commandFlag + " type " + type + " , senderid " + senderId + " content " + new String(content) + "}";
    }
}
