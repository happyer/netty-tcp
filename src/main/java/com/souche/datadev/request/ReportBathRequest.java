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
    private short length; //
    private List<ReportRequest> datas = new ArrayList<>();



    public ReportBathRequest(Header header, ByteBuf byteBuf) {

        count = byteBuf.readShort();
        type = byteBuf.readByte();
        length = byteBuf.readShort();
        ByteBuf byteBuf1 = CodecUtils.reverseTransform(byteBuf);
        do {
            ByteBuf byteBuf2 = CodecUtils.getObject(byteBuf1);
            Header header1 = new KMHeader(byteBuf2);
            ReportRequest reportRequest = new ReportRequest(header1, byteBuf2);
            datas.add(reportRequest);
        }while (byteBuf1.isReadable());

    }

    public short getCount() {
        return count;
    }

    public byte getType() {
        return type;
    }

    public short getLength() {
        return length;
    }

    public List<ReportRequest> getDatas() {
        return datas;
    }
}
