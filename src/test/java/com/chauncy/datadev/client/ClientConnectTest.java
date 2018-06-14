package com.chauncy.datadev.client;

import com.chauncy.datadev.client.handler.ConnectionClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by chauncy on 2018/6/12.
 */
public class ClientConnectTest {


    private final String host;
    private final int port;
    private Bootstrap bootstrap;
    private int number;

    public ClientConnectTest(String host, int port, int number) {
        this.host = host;
        this.port = port;
        this.number = number;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ConnectionClientHandler());
                        }
                    });

            for (int i = 0; i < number; i++) {
                ChannelFuture f = bootstrap.connect(host, port).sync();

            }

        } finally {
            group.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int number = 60000;
        ClientConnectTest clientConnectTest = new ClientConnectTest("115.29.174.15", 6688,number );
        clientConnectTest.start();

    }

}
