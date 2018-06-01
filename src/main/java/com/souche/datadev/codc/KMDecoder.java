package com.souche.datadev.codc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * Created by chauncy on 2018/5/29.
 */
public class KMDecoder extends ByteToMessageDecoder {

    static final byte HEAD_FLAG = 0x7e;
    static final byte TAIL_FLAG = 0x7e;

    private static final int BASE_HEAD_SIZE = 12;


//    private static int count = 0;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        Object o = decode(ctx, buf);
        if (o != null) {
            out.add(o);
        }

    }


    private Object decode2(ChannelHandlerContext ctx, ByteBuf in) {


        //1 可读长度必须大于基本长度: 头的基本长度+头和尾的长度+校验码的长度
        if (in.readableBytes() > BASE_HEAD_SIZE + 2 + 1) {
            //3 寻找第一个 head_flag

            while (in.readableBytes() > BASE_HEAD_SIZE) {
                byte magicNumber = in.readByte();
                if (magicNumber == HEAD_FLAG) break;
            }
            //如果是因为没有字节么有可读,那就直接返回
            if (in.readableBytes() < BASE_HEAD_SIZE) {
                in.readerIndex(in.readerIndex() - 1);
                return null;
            }

            //说明已经找到了开始的head_flag
            int beginIndex = in.readerIndex();


            //test

//            System.out.println("decode call count=" + ++count);
//            System.out.println("bufferIn.readableBytes()=" + in.readableBytes());
//            System.out.println("beginIndex=" + beginIndex);

            //endtest


            //4 首先读取消息头的属性
            short attr = in.getShort(beginIndex + 2);
            int bodyLength = getBodyLength(attr);
            int totalLength = BASE_HEAD_SIZE + bodyLength + 1;
            //5 读取totalLength 之后的一个字节,判断是否是 结尾的标识符
            //检查是否有这么多自己可读,如果小于这么多字节,那就直接返回null
            if (in.readableBytes() < totalLength + 1) {
                in.readerIndex(beginIndex - 1);
                return null;
            }
            byte tailMagicNumber = in.getByte(beginIndex + totalLength);
            if (tailMagicNumber == TAIL_FLAG) {
                in.skipBytes(totalLength + 1);
                return in.slice(beginIndex, totalLength).retain();
            } else {
                in.skipBytes(totalLength + 1);
                return null;
            }

        }
        return null;

    }


    private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        //1 可读长度必须大于基本长度: 头的基本长度+头和尾的长度+校验码的长度
        if (in.readableBytes() >= BASE_HEAD_SIZE + 2 + 1) {


//            System.out.println("bufferIn.readableBytes()=" + in.readableBytes());
//            System.out.println("decode call count=" + ++count);
//            System.out.println("in.readerIndex() = " + in.readerIndex());

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


            //test

//            System.out.println("beginIndex=" + beginIndex);
//            System.out.println("endIndex=" + endIndex);

            //endtest


            //如果找不到 结尾的tail_flag 那就直接跳过,返回null
            if (endIndex == -1) {
                in.readerIndex(beginIndex - 1);
                return null;
            }

            //开始的head_flag 和tail_flag 都已经找到了,那几进行解析,和判断

            //进行转义,将数据读取出来进行转义
            ByteBuf byteBuf1 = reverseTransform(in.slice(beginIndex, (endIndex - beginIndex)));
            //首先读取消息头的属性
            if (lengthIsRight(byteBuf1)) {
                in.readerIndex(endIndex + 1);
                return byteBuf1.retain();
            }
            //fixme ??可以忽略
//            else {
//                in.readerIndex(endIndex);
//                return null;
//            }
        }
        return null;

    }

    private int findAfterMagicNumber(ByteBuf in) {
        for (int index = in.readerIndex(); index < in.capacity(); index++) {
            if (in.getByte(index) == TAIL_FLAG) {
                return index;
            }

        }
        return -1;
    }

    private static int getBodyLength(short attr) {
        return (attr & 0x3ff);
    }

    /**
     * 是否分包
     *
     * @param attr
     * @return
     */
    private boolean isDivPack(short attr) {
        return ((attr >>> 13) & 0x01) > 0 ? true : false;
    }


    private static final short T1 = 0x7d02;
    private static final short T2 = 0x7d01;

    private static ByteBuf reverseTransform(ByteBuf msg) {
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

    private static boolean lengthIsRight(ByteBuf in) {
        short attr = in.getShort(2);
        int bodyLength = getBodyLength(attr);
        return in.readableBytes() == (1 + BASE_HEAD_SIZE + bodyLength);
    }

}
