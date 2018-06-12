package com.souche.datadev.mq;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Created by chauncy on 2018/6/12.
 */
public class Producer {


    private final KafkaProducer<Integer, String> producer;
    private final Boolean isAsync;  //all async

    private static final Producer INSTANCE = new Producer(true);

    private Producer(Boolean isAsync) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
        this.isAsync = isAsync;
    }


    public static Producer getInstance() {
        return INSTANCE;
    }

    public void close() {
        producer.close();
    }


    public void send(String topicName, String key, String value) {
        producer.send(new ProducerRecord(topicName, key, value));
    }



}
