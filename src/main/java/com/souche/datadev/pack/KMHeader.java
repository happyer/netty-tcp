package com.souche.datadev.pack;

import com.souche.datadev.utils.BCDUtils;
import io.netty.buffer.ByteBuf;

/**
 * Created by chauncy on 2018/5/29.
 */
public class KMHeader implements Header {


    private short id;
    private short length;             //body length
    private EncryptType encryptType;  //加密类型
    private boolean isDivPack;  //是否分包
    private String phone;   //sim 号码
    private short number;  //流水号


    public KMHeader(ByteBuf msg) {

        id = msg.readShort();
        //消息体属性
        short attr = msg.readShort();
        length = (short) (attr & 0x7ff);
        encryptType = ((attr >>> 10) & 0x01) > 1 ? EncryptType.RSA : EncryptType.None;
        byte [] b = new byte[6];
        msg.readBytes(b);
        phone = BCDUtils.getPhoneFromBCD(b);
        number = msg.readShort();
    }



    @Override
    public short getMsgId() {
        return id;
    }


    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public short getMsgNumber() {
        return number;
    }

    @Override
    public short getLength() {
        return length;
    }

    @Override
    public EncryptType getEncryptType() {
        return encryptType;
    }



}
