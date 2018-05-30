package com.souche.datadev.handler;

import com.souche.datadev.holder.ClientMap;
import com.souche.datadev.pack.Header;
import com.souche.datadev.pack.KMHeader;
import com.sun.deploy.util.SessionState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by chauncy on 2018/5/29.
 */
public class GpsDispatcHandler extends ChannelInboundHandlerAdapter {


    //todo
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        if (msg instanceof ByteBuf) {
            Header header = new KMHeader((ByteBuf) msg);
            System.out.println("header = " + header.getMsgId());
        }


    }

    //todo
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientMap.add(ctx.toString(), ctx.channel());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientMap.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (this == ctx.pipeline().last()) {
            //todo ended
        }
        ctx.channel().close();
        ctx.close();
    }
}
