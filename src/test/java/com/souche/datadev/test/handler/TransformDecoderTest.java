package com.souche.datadev.test.handler;

import com.souche.datadev.test.AbstractDecoder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by chauncy on 2018/6/1.
 */
public class TransformDecoderTest extends AbstractDecoder {


    @Test
    public void name() throws Exception {
    }

    @Test
    public void testTransform() {
//        ByteBuf byteBuf = get();
//        ByteBuf input = byteBuf.copy();
//        EmbeddedChannel channel = new EmbeddedChannel(new KMDecoder());
//        assertTrue(channel.writeInbound(input.retain()));
//        assertTrue(channel.finish());
//        Object read = channel.readInbound();
//        assertTrue(read instanceof KMHeader);
//        Header kmPack = (KMHeader)read;
//        assertEquals(0x0200, kmPack.getId());
//        assertEquals(0x22, kmPack.getLength());
//
//        byteBuf.release();
    }


}
