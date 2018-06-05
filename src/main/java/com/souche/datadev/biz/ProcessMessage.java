package com.souche.datadev.biz;

import com.souche.datadev.pack.Body;
import com.souche.datadev.pack.Header;
import com.souche.datadev.pack.KMBody;
import com.souche.datadev.request.RegisterRequest;
import com.souche.datadev.request.ReportRequest;
import com.souche.datadev.response.CommonResponse;
import com.souche.datadev.response.RegisterResponse;
import com.souche.datadev.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chauncy on 2018/6/4.
 */
public class ProcessMessage {


    private final Logger logger = LoggerFactory.getLogger(ProcessMessage.class);

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
        //判断是否已经注册过


        logger.info("register provinceId={},cityId={},vendorId={},type={},sn={}",
                registerRequest.getProvinceId(), registerRequest.getCityId(), registerRequest.getVendorId(), registerRequest.getTerminalType(), header.getMsgNumber());

        Response registerResponse = new RegisterResponse(header.getPhone(), header.getMsgNumber(), Response.STATUS_SUCCESS, new String("token"));


        ctx.writeAndFlush(registerResponse.response());

    }

    public void doTerminalAuth() {
        int length = header.getLength();
        byte[] token = new byte[length];
        buf.readBytes(token);
//        logger.info("terminal auth length={},token={}", length, new String(token));
        responseCommon();
    }

    public void doHeart() {
        responseCommon();
        logger.info("do heart done phone={}", header.getPhone());
    }

    public void doLocationReport() {
        ReportRequest reportRequest = new ReportRequest(header, buf);

        Body body = new KMBody(buf, header);

        //todo send mq

        responseCommon();
        logger.info("location report gpsTime={} ,lat={},lon={} location={} ",
                body.getTime(), body.getLatitude(), body.getLongitude(),body.getLocation());
    }

    private void responseCommon() {
        Response response = new CommonResponse(header.getPhone(), header.getMsgNumber(), header.getMsgId(), Response.STATUS_SUCCESS);
        ctx.write(response.response());
    }
}
