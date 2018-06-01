package com.souche.datadev.handler;

import com.souche.datadev.holder.ClientHolder;
import com.souche.datadev.pack.KMPack;
import com.souche.datadev.pack.MessagId;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chauncy on 2018/5/29.
 */
public class GpsBizHandler extends ChannelInboundHandlerAdapter {


    private static Logger logger = LoggerFactory.getLogger(GpsBizHandler.class);


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
        logger.info(String.format("recv phone = %s report status= %d  lat=%d lon=%d", pack.getHeader().getPhone(), pack.getBody().getStatus(), pack.getBody().getLatitude(), pack.getBody().getLongitude()));
    }

    private void doHeart(KMPack pack) {
        logger.info(String.format("recv phone = %s heart  = ", pack.getHeader().getPhone()));
    }


    //todo
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if ( evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent) evt;

            switch (e.state()){
                case ALL_IDLE:
                    logger.info("phone ={}  is all idle",ClientHolder.getPhone(ctx.channel()));
                    break;
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    break;
                default:
                    break;
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("phone={} is disconnect", ClientHolder.getPhone(ctx.channel()));
        ClientHolder.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (this == ctx.pipeline().last()) {
            //todo ended
        }
        logger.info("phone={} is disconnect", ClientHolder.getPhone(ctx.channel()));
        ClientHolder.remove(ctx.channel());
        ctx.channel().close();
        ctx.close();
    }
}
