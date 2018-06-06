package com.souche.datadev.test.handler;

import com.souche.datadev.codc.KMDecoder;
import com.souche.datadev.test.AbstractDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by chauncy on 2018/6/1.
 */
public class KMDecoderTest extends AbstractDecoder {



    @Test
    public void testDecoder() {

        ByteBuf byteBuf = get();
        ByteBuf input  = byteBuf.copy();
        EmbeddedChannel channel = new EmbeddedChannel(new KMDecoder());
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        ByteBuf read = channel.readInbound();
        assertEquals(byteBuf.slice(1,byteBuf.readableBytes()-2),read);
        read.release();
        byteBuf.release();



    }



}
