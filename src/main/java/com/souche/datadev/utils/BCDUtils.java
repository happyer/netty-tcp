package com.souche.datadev.utils;

/**
 * Created by chauncy on 2018/5/31.
 */
public class BCDUtils {

    public static String getPhoneFromBCD(byte[] buf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : buf) {
            stringBuilder.append(encoderBCD(b));
        }
        return stringBuilder.toString();
    }


    public static String encoderBCD(byte b) {
        int lower = (b & 0x0f);
        int higher = b >>> 4;

        return higher + "" + lower;
    }
}
