package com.souche.datadev.test.handler;

import com.souche.datadev.utils.BitUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chauncy on 2018/6/1.
 */
public abstract class AbstractDecoder {

    private  final String tcpData = "7E 02 00 00 22 01 44 00 44 00 55 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 18 05 30 17 31 36 25 04 00 00 00 00 69 7E";

    protected ByteBuf get() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (String s : tcpData.split(" ")) {
            byteBuf.writeByte(BitUtils.hexStringToByteArray(s)[0]);
        }
        return byteBuf;
    }
}
