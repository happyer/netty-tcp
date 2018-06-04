package com.souche.datadev.handler;

import com.souche.datadev.codc.KMDecoder;
import com.souche.datadev.codc.KMEncoder;
import com.souche.datadev.codc.TransformDecoder;
import com.souche.datadev.codc.TransformEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by chauncy on 2018/5/29.
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    private static final int IDEL_TIME_OUT = 10;
    private static final int READ_IDEL_TIME_OUT = 4;
    private static final int WRITE_IDEL_TIME_OUT = 5;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new KMDecoder());
        ch.pipeline().addLast(new KMEncoder());
//        ch.pipeline().addLast(new TransformDecoder());
        ch.pipeline().addLast(new TransformEncoder());
        ch.pipeline().addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, IDEL_TIME_OUT));
//        ch.pipeline().addLast(new GpsIdleHandler());
        ch.pipeline().addLast(new GpsBizHandler());
    }
}
