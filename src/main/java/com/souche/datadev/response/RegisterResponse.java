package com.souche.datadev.response;

import com.souche.datadev.utils.BCDUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chauncy on 2018/6/4.
 */
public class RegisterResponse implements Response {


    private short number;
    private byte res;
    private String tokeCode;
    private String phone;


    public RegisterResponse(String phone, short number, byte res, String tokeCode) {
        this.number = number;
        this.res = res;
        this.tokeCode = tokeCode;
        this.phone = phone;
    }

    @Override
    public ByteBuf response() {

        ByteBuf byteBuf = Unpooled.directBuffer();
        byteBuf.writeShort(0x8100);
        //流水号+状态+token长度
        byteBuf.writeShort(2 + 1 + tokeCode.getBytes().length);
        //电话
        byteBuf.writeBytes(BCDUtils.encodeToBCDArray(Long.parseLong(phone)));
        byteBuf.writeShort(number);
        //消息体
        byteBuf.writeShort(number);
        byteBuf.writeByte(res);
        byteBuf.writeBytes(tokeCode.getBytes());
        return byteBuf;
    }

}
