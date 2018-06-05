package com.souche.datadev.response;

import com.souche.datadev.utils.BCDUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chauncy on 2018/6/4.
 */
public class CommonResponse implements Response {

    private final short number;
    private final byte res;
    private final String phone;
    private final short terminalId;


    public CommonResponse(String phone, short number, short terminalId, byte res) {
        this.number = number;
        this.res = res;
        this.phone = phone;
        this.terminalId = terminalId;
    }

    @Override
    public ByteBuf response() {

        ByteBuf byteBuf = Unpooled.directBuffer();

        byteBuf.writeShort(0x8001);
        //流水号+终端id+状态
        byteBuf.writeShort(2 + 2 + 1);
        byteBuf.writeBytes(BCDUtils.encodeToBCDArray(Long.parseLong(phone)));
        byteBuf.writeShort(number);
        //消息体
        byteBuf.writeShort(number);
        byteBuf.writeShort(terminalId);
        byteBuf.writeByte(res);
        return byteBuf;
    }
}
