package com.rin.vertx.code.codec;

import com.rin.vertx.code.entities.CommandFlag;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.PacketImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by duongittien
 */
public class Decoder extends ReplayingDecoder<Decoder.DecodingState> {
    private Packet packet;

    public Decoder() {
        this.reset();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) throws Exception {
        switch (state()) {
            case FLAG: {
                if (buffer.readableBytes() < Packet.B_FLAG_LENGTH) {
                    return;
                }
                CommandFlag commandFlag = new CommandFlag();
                this.packet.setCommandFlag(commandFlag.fromByte(buffer.readByte()));
                checkpoint(DecodingState.LENGTH);
            }
            break;
            case LENGTH: {
                buffer.markReaderIndex();
                if (buffer.readableBytes() < Packet.B_DATA_LENGTH) {
                    buffer.resetReaderIndex();
                    return;
                }
                int size = buffer.readInt();
                if (size < 0) {
                    list.add(this.packet);
                    reset();
                } else {
                    this.packet.setDataLength(size);
                    checkpoint(DecodingState.CODE);
                }
            }
            break;
            case CODE: {
                buffer.markReaderIndex();
                if (buffer.readableBytes() < Packet.B_CODE_LENGTH) {
                    buffer.resetReaderIndex();
                    return;
                }
                byte[] bCode = new byte[Packet.B_CODE_LENGTH];
                buffer.readBytes(bCode, 0, bCode.length);
                this.packet.setCode(new String(bCode));
                checkpoint(DecodingState.TYPE);
            }
            break;
            case TYPE: {
                buffer.markReaderIndex();
                if (buffer.readableBytes() < Packet.B_CODE_LENGTH) {
                    buffer.resetReaderIndex();
                    return;
                }
                byte bType = buffer.readByte();
                this.packet.setType(bType);
                checkpoint(DecodingState.SENDER_ID);
            }
            break;
            case SENDER_ID: {
                buffer.markReaderIndex();
                if (buffer.readableBytes() < Packet.B_SENDER_LENGTH) {
                    buffer.resetReaderIndex();
                    return;
                }
                int senderId = buffer.readInt();
                this.packet.setSenderId(senderId);
                checkpoint(DecodingState.CONTENT);
            }
            break;
            case CONTENT:
                int contentLength = this.packet.getDataLength();
                if (contentLength > 0) {
                    buffer.markReaderIndex();
                    if (buffer.readableBytes() < contentLength) {
                        buffer.resetReaderIndex();
                        return;
                    }
                    byte[] bContent = new byte[contentLength];
                    buffer.readBytes(bContent, 0, contentLength);
                    this.packet.setContent(bContent);
                }
                list.add(this.packet);
                reset();
                break;
            default:
                throw new Exception("Unknown decoding state: " + state());
        }
    }

    private void reset() {
        checkpoint(DecodingState.FLAG);
        this.packet = new PacketImpl();
    }

    public enum DecodingState {
        FLAG,
        LENGTH,
        CODE,
        TYPE,
        SENDER_ID,
        CONTENT
    }

}
