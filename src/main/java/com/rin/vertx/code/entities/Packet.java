package com.rin.vertx.code.entities;

/**
 * Created by duongittien
 */
public interface Packet {
    static final int B_CODE_LENGTH = 3;
    static final int B_FLAG_LENGTH = 1;
    static final int B_TYPE_LENGTH = 1;
    static final int B_DATA_LENGTH = 4;
    static final int B_SENDER_LENGTH = 4;
    static final int B_COMMAND_LENGTH = B_CODE_LENGTH + B_TYPE_LENGTH + B_SENDER_LENGTH;
    static final int B_HEADER_COMMAND_LENGTH = B_COMMAND_LENGTH + B_FLAG_LENGTH + B_DATA_LENGTH;

    CommandFlag getCommandFlag();

    void setCommandFlag(CommandFlag commandFlag);

    int getDataLength();

    void setDataLength(int dataLength);

    String getCode();

    void setCode(String code);

    byte getType();

    void setType(byte type);

    int getSenderId();

    void setSenderId(int senderId);

    int getFlag();

    byte[] getContent();

    void setContent(byte[] content);

    void setContent(Object obj);

}
