package com.souche.datadev.pack;

/**
 * Created by chauncy on 2018/5/29.
 */
public interface Header {

    short getId();

    String getPhone();

    short getNo();

    short getLength();

    EncryptType getEncryptType();
}
