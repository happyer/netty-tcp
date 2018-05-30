package com.souche.datadev.pack;

/**
 * Created by chauncy on 2018/5/29.
 */
public interface MsgAttr {


    short getLength();

    EncryptType getEncryptType();

    boolean isDivisionPack();

}
