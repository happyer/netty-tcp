package com.souche.datadev.biz;

import com.souche.datadev.pack.Header;
import com.souche.datadev.request.RegisterRequest;
import com.souche.datadev.request.ReportRequest;
import com.souche.datadev.response.CommonResponse;
import com.souche.datadev.response.RegisterResponse;
import com.souche.datadev.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by chauncy on 2018/6/4.
 */
public class ProcessMessage {


    private ChannelHandlerContext ctx;
    private Header header;
    private ByteBuf buf;

    public ProcessMessage(ChannelHandlerContext ctx, Header header, ByteBuf buf) {
        this.ctx = ctx;
        this.header = header;
        this.buf = buf;
    }

    public void doRegister() {
        RegisterRequest registerRequest = new RegisterRequest(header, buf);
        //处理响应的业务逻辑
        Response registerResponse = new RegisterResponse(registerRequest);
        ctx.writeAndFlush(registerResponse.response());
    }

    public void doTerminalAuth() {
        responseCommon();
    }

    public void doHeart() {
        responseCommon();
    }

    public void doLocationReport() {
        ReportRequest reportRequest = new ReportRequest(header, buf);
        //todo send mq
        responseCommon();
    }

    private void responseCommon() {
        Response response = new CommonResponse(header.getMsgNumber(), header.getMsgId(), Response.SUCCESS);
        ctx.writeAndFlush(response.response());
    }
}
