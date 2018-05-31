package com.souche.datadev.pack;

import io.netty.buffer.ByteBuf;

/**
 * Created by chauncy on 2018/5/31.
 */
public class KMBody implements Body {

    private int alarm;
    private int status;
    private int latitude;
    private int longitude;
    private short altitude;
    private short speed;
    private short director;
    private byte[] time;


    public KMBody(ByteBuf msg, Header header) {
        if (header.getMsgId() == 0x200) {
            alarm = msg.readInt();
            status = msg.readInt();
            latitude = msg.readInt();
            longitude = msg.readInt();
            altitude = msg.readShort();
            speed = msg.readShort();
            director = msg.readShort();
            time = new byte[6];
            msg.readBytes(time);
        }
    }


    @Override
    public int getAlarm() {
        return alarm;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public int getLatitude() {
        return latitude;
    }

    @Override
    public int getLongitude() {
        return longitude;
    }

    @Override
    public short getAltitude() {
        return altitude;
    }

    @Override
    public short getSpeed() {
        return speed;
    }

    @Override
    public short getDirect() {
        return director;
    }

    @Override
    public byte[] getTime() {
        return time;
    }
}
