package com.chauncy.datadev.pack;

/**
 * Created by chauncy on 2018/5/29.
 */
public interface Body {


    AlarmType getAlarm();

    int getLatitude();

    int getLongitude();

    short getAltitude();  //m

    short getSpeed();  //1/10km/h

    short getDirect();  //0-359 正北 为0 顺时针

    String getTime(); //YY-MM-DD-hh-mm-ss (BCD8421)

    int getAcc();   //0 close 1-open

    int getLocation();  //0 not location 1-location

    int getLongitudeType();  //0-北纬  1-南纬

    int getLatitudeType();  //0-东经  1-西经

    int getOilPipe();  //0-油路正常 1-油路断开
}
