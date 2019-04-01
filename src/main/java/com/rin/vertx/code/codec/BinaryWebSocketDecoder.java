package com.rin.vertx.code.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * Created by duongittien
 */
public class BinaryWebSocketDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame msg, List<Object> list) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer(msg.content());
        list.add(buf);
    }
}
