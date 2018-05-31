package com.souche.datadev.codc;

import com.souche.datadev.pack.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by chauncy on 2018/5/30.
 */
public class TransformDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        Object o = decode(ctx, msg);
        if (o != null) {
            out.add(o);
        }

    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf msg) {

        Header header = new KMHeader(msg);
        Body body = new KMBody(msg,header);

        return new KMPack(header, body);

    }
}
