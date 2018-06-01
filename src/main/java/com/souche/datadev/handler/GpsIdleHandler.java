package com.souche.datadev.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by chauncy on 2018/5/29.
 */
public class GpsIdleHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if ( evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent) evt;

            switch (e.state()){
                case ALL_IDLE:

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
}
