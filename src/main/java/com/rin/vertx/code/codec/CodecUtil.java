package com.rin.vertx.code.codec;

import com.rin.vertx.code.entities.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by duongittien
 */
public class CodecUtil {
    public static ByteBuf encodeMessage(String s) throws IllegalArgumentException {
        byte[] contentByteArr = s.getBytes();
        int size = contentByteArr.length;
        ByteBuf buffer = Unpooled.buffer(size);
        buffer.writeBytes(contentByteArr);
        return buffer;
    }

    public static ByteBuf encodeMessage(Packet packet) throws IllegalArgumentException {
        byte[] contentByteArr = packet.getContent();
        int size = Packet.B_HEADER_COMMAND_LENGTH + contentByteArr.length;
        ByteBuf buffer = Unpooled.buffer(size);
        buffer.writeByte(packet.getCommandFlag().toByte());
        buffer.writeInt(packet.getDataLength());
        buffer.writeBytes(packet.getCode().getBytes());
        buffer.writeByte(packet.getType());
        buffer.writeInt(packet.getSenderId());
        buffer.writeBytes(contentByteArr);
        return buffer;
    }
}
