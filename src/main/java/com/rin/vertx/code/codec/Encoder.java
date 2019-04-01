package com.rin.vertx.code.codec;

import com.rin.vertx.code.entities.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by duongittien
 */
@ChannelHandler.Sharable
public class Encoder extends MessageToMessageEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        list.add(CodecUtil.encodeMessage(packet));
    }
    public static Encoder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final Encoder INSTANCE = new Encoder();
    }
}
