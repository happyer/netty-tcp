package com.souche.datadev.request;

import com.souche.datadev.pack.Header;
import com.souche.datadev.pack.KMHeader;
import com.souche.datadev.utils.CodecUtils;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chauncy on 2018/6/5.
 */
public class ReportBathRequest {

    private short count;
    private byte type;  //0 正常批量数据 1盲区补报
    private List<ReportRequest> datas = new ArrayList<>();


    public ReportBathRequest(Header header, ByteBuf byteBuf) {

        count = byteBuf.readShort();
        type = byteBuf.readByte();
        do {
          short  length = byteBuf.readShort();
            ByteBuf rpBuf = byteBuf.slice(byteBuf.readerIndex(), length);
            ReportRequest reportRequest = new ReportRequest(header, rpBuf);
            datas.add(reportRequest);
            byteBuf.skipBytes(length);
        } while (byteBuf.isReadable());


    }

    public short getCount() {
        return count;
    }

    public byte getType() {
        return type;
    }


    public List<ReportRequest> getDatas() {
        return datas;
    }
}
