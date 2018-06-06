package com.souche.datadev.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chauncy on 2018/6/5.
 */
public class CodecUtils {


    private static final byte HEAD_FLAG = 0x7e;
    private static final byte TAIL_FLAG = 0x7e;

    private static final short T1 = 0x7d02;

    private static final int BASE_HEAD_SIZE = 12;

    private static final short T2 = 0x7d01;


    /**
     * 去掉 head  tail 的flag
     *
     * @param in
     * @return
     */
    public static ByteBuf getCutFlagObject(ByteBuf in) {

        if (in.readableBytes() >= BASE_HEAD_SIZE + 2 + 1) {

            while (in.readableBytes() > 0) {
                byte magicNumber = in.readByte();
                if (magicNumber == HEAD_FLAG) break;
            }

            //说明已经找到了开始的head_flag
            int beginIndex = in.readerIndex();

            //如果是应为没有字节么有可读,那就直接返回
            if (in.readableBytes() < 0) {
                in.readerIndex(beginIndex - 1);
                return null;
            }


            // 寻找后面的 tail_flag
            int endIndex = findAfterMagicNumber(in);


            //如果找不到 结尾的tail_flag 那就直接跳过,返回null
            if (endIndex == -1) {
                in.readerIndex(beginIndex - 1);
                return null;
            }

            //开始的head_flag 和tail_flag 都已经找到了,那几进行解析,和判断
            return in.slice(beginIndex, endIndex - beginIndex);

        }
        return null;
    }


    public static ByteBuf getObject(ByteBuf in) {

        if (in.readableBytes() >= BASE_HEAD_SIZE + 2 + 1) {

            while (in.readableBytes() > 0) {
                byte magicNumber = in.readByte();
                if (magicNumber == HEAD_FLAG) break;
            }

            //说明已经找到了开始的head_flag
            int beginIndex = in.readerIndex();

            //如果是应为没有字节么有可读,那就直接返回
            if (in.readableBytes() < 0) {
                in.readerIndex(beginIndex - 1);
                return null;
            }


            // 寻找后面的 tail_flag
            int endIndex = findAfterMagicNumber(in);


            //如果找不到 结尾的tail_flag 那就直接跳过,返回null
            if (endIndex == -1) {
                in.readerIndex(beginIndex - 1);
                return null;
            }

            //开始的head_flag 和tail_flag 都已经找到了,那几进行解析,和判断

            //进行转义,将数据读取出来进行转义
            ByteBuf byteBuf1 = reverseTransform(in.slice(beginIndex, (endIndex - beginIndex)));
            //去掉crc
            ByteBuf cutCrc = cutCrc(byteBuf1);
            //首先读取消息头的属性
            if (lengthIsRight(cutCrc)) {
                in.readerIndex(endIndex + 1);
                return cutCrc;
            }
        }
        return null;
    }

    /**
     * 进行一个转义
     * 返回一个新的bytebuf in java heap
     *
     * @param msg
     * @return
     */
    public static ByteBuf reverseTransform(ByteBuf msg) {
        ByteBuf byteBuf = Unpooled.buffer();
        while (msg.readableBytes() > 0) {
            if (msg.readableBytes() >= 2) {
                short red = msg.getShort(msg.readerIndex());
                if (red == T1) {
                    byteBuf.writeByte(0x7e);
                    msg.readShort();
                    continue;
                }
                if (red == T2) {
                    byteBuf.writeByte(0x7d);
                    msg.readShort();
                    continue;
                }
            }
            byteBuf.writeByte(msg.readByte());

        }
        return byteBuf;
    }

    /**
     * 去掉crc
     *
     * @param byteBuf
     * @return
     */
    public static ByteBuf cutCrc(ByteBuf byteBuf) {
        return byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes() - 1);
    }

    private static int findAfterMagicNumber(ByteBuf in) {

        return in.forEachByte(in.readerIndex(), in.readableBytes(), value -> value != TAIL_FLAG);


    }

    private static boolean lengthIsRight(ByteBuf in) {
        short attr = in.getShort(2);
        int bodyLength = getBodyLength(attr);
        return in.readableBytes() == (BASE_HEAD_SIZE + bodyLength);
    }

    private static int getBodyLength(short attr) {
        return (attr & 0x3ff);
    }

}
