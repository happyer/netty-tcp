package com.souche.datadev.utils;

import io.netty.buffer.ByteBufUtil;

/**
 * Created by chauncy on 2018/5/31.
 */
public class BCDUtils {

    public static String getPhoneFromBCD(byte[] buf) {
        return ByteBufUtil.hexDump(buf);
    }


}
