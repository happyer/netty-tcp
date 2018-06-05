package com.souche.datadev.test;

import com.souche.datadev.utils.BitUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chauncy on 2018/6/5.
 */
public class BytebufTest {


    private static String heartData = "7E 00 02 00 00 01 86 57 11 59 29 00 01 69 7E";


    private int res = 14;

    public static ByteBuf getHeart() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (String s : heartData.split(" ")) {
            byteBuf.writeByte(BitUtils.hexStringToByteArray(s)[0]);
        }
        return byteBuf;
    }


    @Test
    public void testForEach() {
        ByteBuf byteBuf = getHeart();
        int offset = byteBuf.forEachByte(byteBuf.readerIndex() + 1, byteBuf.readableBytes(), value -> value != 0x7e);
            Assert.assertEquals(res, offset);
        }

        @Test
        public void testIndexOf () {
            //not contain last
            ByteBuf byteBuf = getHeart();
            int offset = byteBuf.bytesBefore(byteBuf.readerIndex() + 1, byteBuf.readableBytes(), (byte) 0x7e);
            Assert.assertEquals(res - 1, offset);
        }

    }
