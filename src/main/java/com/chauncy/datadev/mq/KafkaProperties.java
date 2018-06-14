package com.chauncy.datadev.mq;

/**
 * Created by chauncy on 2018/6/12.
 */
public class KafkaProperties {

    public static final String TOPIC = "gps-km-platform";
    public static final String KAFKA_SERVER_URL = "172.17.41.19:6667,172.17.40.241:6667";
    public static final int KAFKA_SERVER_PORT = 6667;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {
    }
}
