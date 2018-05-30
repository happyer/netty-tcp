package com.souche.datadev.client;

import com.souche.datadev.client.handler.KMClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.omg.PortableInterceptor.HOLDING;

import java.net.InetSocketAddress;

/**
 * Created by chauncy on 2018/5/30.
 */
public class KMClient {

    private final String host;
    private final int port;


    public KMClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new KMClientHandler());
                        }
                    });
            ChannelFuture f = bootstrap.connect().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String host ="localhost";
        int port  = 9090;
        KMClient kmClient = new KMClient(host,port);
        kmClient.start();

    }


}
