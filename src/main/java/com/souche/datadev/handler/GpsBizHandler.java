package com.souche.datadev.handler;

import com.souche.datadev.biz.ProcessMessage;
import com.souche.datadev.holder.ClientHolder;
import com.souche.datadev.pack.Header;
import com.souche.datadev.pack.KMHeader;
import com.souche.datadev.pack.MessagId;
import io.netty.buffer.ByteBuf;
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
        if (msg instanceof ByteBuf) {


            ByteBuf buf = (ByteBuf) msg;
            Header header = new KMHeader(buf);

            //保持长连接
            ClientHolder.add(header, ctx.channel());

            ProcessMessage processMessge = new ProcessMessage(ctx, header, buf);
            switch (MessagId.getVal(header.getMsgId())) {
                case TERMINAL_REGISTER:
                    processMessge.doRegister();
                    break;
                case TERMINAL_AUTH:
                    processMessge.doTerminalAuth();
                    break;
                case TERMINAL_HEART:
                    processMessge.doHeart();
                    break;
                case LOCATION_REPORT:
                    processMessge.doLocationReport();
                    break;
                default:
                    System.out.println("ctx.channel() = " + ctx.channel());
                    break;
            }
        }
    }


    //todo
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ctx.channel()  active = " + ctx.channel());
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;

            switch (e.state()) {
                case ALL_IDLE:
//                    logger.info("phone ={}  is all idle", ClientHolder.getPhone(ctx.channel()));
                    break;
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    break;
                default:
                    break;
            }
        } else {
            super.userEventTriggered(ctx, evt);
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
        logger.info(" exception phone={} is disconnect", ClientHolder.getPhone(ctx.channel()));
        ClientHolder.remove(ctx.channel());
        ctx.channel().close();
        ctx.close();
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
