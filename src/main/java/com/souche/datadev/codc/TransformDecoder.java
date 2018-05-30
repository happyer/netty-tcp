package com.souche.datadev.codc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by chauncy on 2018/5/30.
 */
public class TransformDecoder extends MessageToMessageDecoder<ByteBuf> {


    private static final short T1 = 0x7d02;
    private static final short T2 = 0x7d01;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        Object o = decode(ctx, msg);
        if (o != null) {
            out.add(o);
        }

    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf msg) {

        ByteBuf byteBuf = Unpooled.buffer();
        while (msg.readableBytes() > 0) {
            if (msg.readableBytes() >= 2) {
                short red = msg.getShort(msg.readerIndex());
                if (red == T1) {
                    byteBuf.writeByte(0x7e);
                    msg.readShort();
                    continue;
                }
                if (red == T2) {
                    byteBuf.writeByte(0x7d);
                    msg.readShort();
                    continue;
                }
            }
            byteBuf.writeByte(msg.readByte());

        }
        return byteBuf;

    }
}
