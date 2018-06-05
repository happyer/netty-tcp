package com.souche.datadev.codc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by chauncy on 2018/5/30.
 */
public class TransformEncoder extends MessageToMessageEncoder<ByteBuf> {


    private static final byte HEAD_FLAG = 0x7e;
    private static final byte TAIL_FLAG = 0x7e;
    private byte crc;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
