package com.souche.datadev.test.handler;

import com.souche.datadev.codc.KMDecoder;
import com.souche.datadev.codc.TransformDecoder;
import com.souche.datadev.pack.KMPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by chauncy on 2018/6/1.
 */
public class TransformDecoderTest extends AbstractDecoder {


    @Test
    public void testTransform() {
        ByteBuf byteBuf = get();
        ByteBuf input = byteBuf.copy();
        EmbeddedChannel channel = new EmbeddedChannel(new KMDecoder(), new TransformDecoder());
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        Object read = channel.readInbound();
        assertTrue(read instanceof KMPack);
        KMPack kmPack = (KMPack) read;
        assertEquals(0x0200, kmPack.getHeader().getMsgId());
        assertEquals(0x22, kmPack.getHeader().getLength());

        byteBuf.release();
    }


}
