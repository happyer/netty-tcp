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

        Object o = decode2(ctx, buf);
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
        if (in.readableBytes() > BASE_HEAD_SIZE + 2 + 1) {
            //2 防止客户端数据过大,太大是不合理的
            if (in.readableBytes() > 2048) {
                in.skipBytes(in.readableBytes());
            }
            //3 寻找第一个 head_flag

            while (in.readableBytes() > 0) {
                byte magicNumber = in.readByte();
                if (magicNumber == HEAD_FLAG) break;
            }
            //如果是应为没有字节么有可读,那就直接返回
            if (in.readableBytes() < 0) return 0;

            //说明已经找到了开始的head_flag
            int beginIndex = in.readerIndex();
            // 4 寻找后面的 tail_flag
            int endIndex = findAfterMagicNumber(in);
            //如果找不到 结尾的tail_flag 那就直接跳过,返回null
            if (endIndex == -1) {
                in.skipBytes(in.readableBytes());
                return null;
            }
            //5 开始的head_flag 和tail_flag 都已经找到了,那几进行解析,和判断
            //首先读取消息头的属性
            short attr = in.getShort(2);
            int bodyLength = getBodyLength(attr);
            boolean isDivPack = isDivPack(attr);
            //头+消息体+校验+末尾的标识符
            int totalLength = 12 + bodyLength + 1 + 1;
            if (isDivPack) {
                totalLength = 2 + 2;
            }
            //6 如果末尾的tail_flag - 头的开始 == 总长度,那这个包就是一个完整的包
            if (endIndex - beginIndex == totalLength) {
                return Unpooled.copiedBuffer(in).slice(beginIndex, endIndex).retain();
            } else {
                //否则就不是一个完整的包,跳过,返回
                in.skipBytes(endIndex - beginIndex);
                return null;
            }
        }
        return null;

    }

    private int findAfterMagicNumber(ByteBuf in) {
        return in.forEachByte(value -> value == TAIL_FLAG);
    }

    private int getBodyLength(short attr) {
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


}
