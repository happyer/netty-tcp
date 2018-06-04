package com.souche.datadev.response;

import com.souche.datadev.request.RegisterRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by chauncy on 2018/6/4.
 */
public class RegisterResponse implements Response {

    private RegisterRequest registerRequest;

    public RegisterResponse(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;


    }

    @Override
    public ByteBuf response() {

        //做一个写逻辑判断
        return null;
    }
}
