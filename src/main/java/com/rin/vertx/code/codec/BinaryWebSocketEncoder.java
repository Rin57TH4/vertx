package com.rin.vertx.code.codec;

import com.rin.vertx.code.entities.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * Created by duongittien
 */
public class BinaryWebSocketEncoder extends MessageToMessageEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        list.add(new BinaryWebSocketFrame(CodecUtil.encodeMessage(packet)));
    }
    public static Encoder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final Encoder INSTANCE = new Encoder();
    }
}
