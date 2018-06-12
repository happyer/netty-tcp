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
public enum Sender {


    INSTANCE;

    private final KafkaProducer<Integer, String> producer;

    Sender() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "GPS-KM-Producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //roubin
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,DeviceIdPartition.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void close() {
        producer.close();
    }


    public void send(String topicName, String key, String value) {
        producer.send(new ProducerRecord(topicName, key, value));
    }

}
