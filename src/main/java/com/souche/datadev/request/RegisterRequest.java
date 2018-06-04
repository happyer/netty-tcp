package com.souche.datadev.request;

import com.souche.datadev.pack.Header;
import io.netty.buffer.ByteBuf;

/**
 * Created by chauncy on 2018/6/4.
 */
public class RegisterRequest {

    private short provinceId;
    private short cityId;
    private byte[] vendorId;
    private byte[] terminalType;
    private byte[] terminalId;
    private byte plateColor;
    private String vin;

    public RegisterRequest(Header header, ByteBuf buf) {
        provinceId = buf.readShort();
        cityId = buf.readShort();
        vendorId = new byte[5];
        buf.readBytes(vendorId);
        terminalType = new byte[8];
        buf.readBytes(terminalType);
        terminalId = new byte[7];
        buf.readBytes(terminalId);
        plateColor = buf.readByte();
//        vin = buf.
    }



}
