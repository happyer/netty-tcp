package com.souche.datadev.pack;

/**
 * Created by chauncy on 2018/5/29.
 */
public interface Body {


    int getAlarm();

    int getStatus();

    int getLatitude();

    int getLongitude();

    short getAltitude();  //m

    short getSpeed();  //1/10km/h

    short getDirect();  //0-359 正北 为0 顺时针

    byte[] getTime(); //YY-MM-DD-hh-mm-ss (BCD8421)

}
