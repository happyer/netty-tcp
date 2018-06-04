package com.souche.datadev.pack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * Created by chauncy on 2018/5/31.
 */
public class KMBody implements Body {

    private AlarmType alarm;
    private int latitude;
    private int longitude;
    private short altitude;
    private short speed;
    private short director;
    private String time;
    private int acc;
    private int location;
    private int longitudeType;
    private int latitudeType;
    private int oilPipe;


    public KMBody(ByteBuf msg, Header header) {
        if (header.getMsgId() == 0x200) {
            int al = msg.readInt();
            alarm = AlarmType.getValue(al);
            int status = msg.readInt();
            latitude = msg.readInt();
            longitude = msg.readInt();
            altitude = msg.readShort();
            speed = msg.readShort();
            director = msg.readShort();
            byte[] t = new byte[6];
            acc = (status & 0x01) == 0 ? 0 : 1;
            location = (status & 0x02) == 0 ? 0 : 1;
            longitudeType = (status & 0x04) == 0 ? 0 : 1;
            latitudeType = (status & 0x08) == 0 ? 0 : 1;
            oilPipe = (status & 0x0400) == 0 ? 0 : 1;
            msg.readBytes(t);
            time = ByteBufUtil.hexDump(t);
        }
    }


    @Override
    public int getOilPipe() {
        return oilPipe;
    }

    @Override
    public int getAcc() {
        return acc;
    }

    @Override
    public int getLocation() {
        return location;
    }

    @Override
    public int getLongitudeType() {
        return longitudeType;
    }

    @Override
    public int getLatitudeType() {
        return latitudeType;
    }

    @Override
    public AlarmType getAlarm() {
        return alarm;
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
    public String getTime() {
        return time;
    }
}
