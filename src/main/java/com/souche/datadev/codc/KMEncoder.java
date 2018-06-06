package com.souche.datadev.codc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chauncy on 2018/5/29.
 */
public class KMEncoder extends MessageToByteEncoder {

    private final Logger logger = LoggerFactory.getLogger(KMEncoder.class);

    private static final byte HEAD_FLAG = 0x7e;
    private static final byte TR_01_FLAG = 0x7e;
    private static final byte TR_02_FLAG = 0x7d;
    private static final byte TAIL_FLAG = 0x7e;


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf src = (ByteBuf) msg;
            byte crc = getCrc(src);
            src.writeByte(crc);
            //添加消息头
            out.writeByte(HEAD_FLAG);
            //todo body
            out.writeBytes(transform(src));
            //tail
            out.writeByte(TAIL_FLAG);

//            logger.info("encoder buffer hex= {}", ByteBufUtil.hexDump(out));


        }
    }

    private byte getCrc(ByteBuf in) {
        final byte[] crc = {0x00};
        in.forEachByte(value -> {
            crc[0] ^= value;
            return true;
        });
        return crc[0];
    }

    private ByteBuf transform(ByteBuf in) {
        ByteBuf byteBuf = Unpooled.buffer();
        in.forEachByte(value -> {
            if (value == TR_01_FLAG) {
                byteBuf.writeByte(0x7d);
                byteBuf.writeByte(0x02);
                return true;
            } else if (value == TR_02_FLAG) {
                byteBuf.writeByte(0x7d);
                byteBuf.writeByte(0x01);
                return true;
            } else {
                byteBuf.writeByte(value);
                return true;
            }

        });
        return byteBuf;
    }

}
