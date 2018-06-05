package com.souche.datadev.request;

import com.souche.datadev.pack.AlarmType;
import com.souche.datadev.pack.Header;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * Created by chauncy on 2018/6/4.
 */
public class ReportRequest {


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



    public ReportRequest(Header header, ByteBuf msg) {
        if (header.getId() == 0x200) {
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

    public AlarmType getAlarm() {
        return alarm;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public short getAltitude() {
        return altitude;
    }

    public short getSpeed() {
        return speed;
    }

    public short getDirector() {
        return director;
    }

    public String getTime() {
        return time;
    }

    public int getAcc() {
        return acc;
    }

    public int getLocation() {
        return location;
    }

    public int getLongitudeType() {
        return longitudeType;
    }

    public int getLatitudeType() {
        return latitudeType;
    }

    public int getOilPipe() {
        return oilPipe;
    }
}
