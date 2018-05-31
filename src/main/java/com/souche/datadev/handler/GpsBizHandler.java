package com.souche.datadev.handler;

import com.souche.datadev.holder.ClientHolder;
import com.souche.datadev.pack.KMPack;
import com.souche.datadev.pack.MessagId;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by chauncy on 2018/5/29.
 */
public class GpsBizHandler extends ChannelInboundHandlerAdapter {

    //todo
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        messageParseAndReply(ctx, msg);

    }

    private void messageParseAndReply(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof KMPack) {
            KMPack pack = (KMPack) msg;
            //保持长连接
            ClientHolder.add(pack.getHeader(), ctx.channel());
            switch (MessagId.getVal(pack.getHeader().getMsgId())) {
                case TERMINAL_HEART:
                    doHeart(pack);
                    break;
                case LOCATION_REPORT:
                    doLocationReport(pack);
                    break;
                default:
                    break;
            }
        }
    }

    private void doLocationReport(KMPack pack) {
        System.out.println(String.format("recv phone = %s report status= %d  lat=%d lon=%d", pack.getHeader().getPhone(), pack.getBody().getStatus(),pack.getBody().getLatitude(),pack.getBody().getLongitude()));
    }

    private void doHeart(KMPack pack) {
        System.out.println(String.format("recv phone = %s heart  = ", pack.getHeader().getPhone()));
    }


    //todo
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientHolder.remove(ctx.channel());
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
