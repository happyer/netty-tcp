package com.souche.datadev.pack;

/**
 * Created by chauncy on 2018/6/4.
 */
public enum AlarmType {


    EMERGENCY_ALARM,
    OVER_SPEED_ALARM,
    GPS_ANTENNA_DISCARD_ALARM,
    LESS_VOLTAGE_ALARM,
    LOST_POWER_ALARM,
    OTHER;


    private int value;

    public static AlarmType getValue(int val) {
        if ((val & 0x01) == 1) {
            return EMERGENCY_ALARM;
        } else if ((val & 0x02) == 1) {
            return OVER_SPEED_ALARM;
        } else if ((val & 0x20) == 1) {
            return GPS_ANTENNA_DISCARD_ALARM;
        } else if ((val & 0x40) == 1) {
            return LESS_VOLTAGE_ALARM;
        } else if ((val & 0x80) == 1) {
            return LOST_POWER_ALARM;
        } else {
            return OTHER;
        }

    }


}
