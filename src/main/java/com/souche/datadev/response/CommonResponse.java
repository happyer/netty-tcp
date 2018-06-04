package com.souche.datadev.response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chauncy on 2018/6/4.
 */
public class CommonResponse implements Response {

    private short number;
    private short id;
    private byte res;

    public CommonResponse(short number, short id, byte res) {
        this.number = number;
        this.id = id;
        this.res = res;
    }

    @Override
    public ByteBuf response() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeShort(number);
        byteBuf.writeShort(id);
        byteBuf.writeShort(res);
        return byteBuf;
    }
}
