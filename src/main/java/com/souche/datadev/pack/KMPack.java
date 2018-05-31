package com.souche.datadev.pack;

/**
 * Created by chauncy on 2018/5/31.
 */
public class KMPack {


    private Header header;
    private Body body;


    public KMPack(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }
}
