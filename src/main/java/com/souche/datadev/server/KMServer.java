package com.souche.datadev.server;

import com.souche.datadev.codc.KMDecoder;
import com.souche.datadev.codc.KMEncoder;
import com.souche.datadev.handler.GpsBizHandler;
import com.souche.datadev.server.define.AbstractNettyServer;
import com.souche.datadev.server.define.IServer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by chauncy on 2018/6/11.
 */
public class KMServer extends AbstractNettyServer implements IServer,Runnable {

    private int port;

    private static final int IDEL_TIME_OUT = 10;
    private static final int READ_IDEL_TIME_OUT = 4;
    private static final int WRITE_IDEL_TIME_OUT = 5;




    public KMServer(String serverName, int port) {
        super(serverName);
        this.port = port;
    }

    @Override
    public void startServer() throws Exception {
        bind(port);
    }

    @Override
    public void stopServer() throws Exception {
        unbind();
    }


    @Override
    protected void initPipeline(ChannelPipeline pipeline) {
        pipeline.addLast(new KMDecoder());
        pipeline.addLast(new KMEncoder());
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, IDEL_TIME_OUT));
        pipeline.addLast(new GpsBizHandler());
    }

    @Override
    public void run() {
        bind(port);
    }
}
