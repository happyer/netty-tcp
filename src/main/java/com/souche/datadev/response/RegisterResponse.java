package com.souche.datadev.response;

import com.souche.datadev.request.RegisterRequest;
import io.netty.buffer.ByteBuf;

/**
 * Created by chauncy on 2018/6/4.
 */
public class RegisterResponse implements Response {


    private short number;
    private byte res;
    private String tokeCode;


    public RegisterResponse(short number, byte res, String tokeCode) {
        this.number = number;
        this.res = res;
        this.tokeCode = tokeCode;
    }

    @Override
    public ByteBuf response() {

        //
        return null;
    }
}
