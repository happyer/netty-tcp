package com.chauncy.datadev.request;

import com.chauncy.datadev.pack.Header;
import com.chauncy.datadev.pack.AlarmType;
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
    private int distance;
    private String phone;
    private byte[] bsjExtention;


    public ReportRequest() {
    }

    public ReportRequest(Header header, ByteBuf msg) {
        this.phone = header.getPhone();
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
        //读取附加信息
        while (msg.isReadable()) {
            byte mid = msg.readByte();
            byte length = msg.readByte();
            if (mid == 0x01) {
                //里程字节长度
                distance = msg.readInt();
            }
            if (mid == 0xEB) {
                bsjExtention = new byte[length];
                msg.readBytes(bsjExtention);
            }

        }


    }


    public String getPhone() {
        return phone;
    }

    public byte[] getBsjExtention() {
        return bsjExtention;
    }

    public int getDistance() {
        return distance;
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
