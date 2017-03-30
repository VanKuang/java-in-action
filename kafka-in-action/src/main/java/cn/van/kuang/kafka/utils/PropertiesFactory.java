package cn.van.kuang.kafka.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public final class PropertiesFactory {

    public static Properties createStringProducerProperties() {
        Properties props = createConsumerCommonProperties();
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public static Properties createStringConsumerProperties() {
        Properties props = createProducerCommonProperties();
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public static Properties createAvroProducerProperties() {
        Properties props = createProducerCommonProperties();
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        return props;
    }

    public static Properties createAvroConsumerProperties() {
        Properties props = createConsumerCommonProperties();
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return props;
    }

    private static Properties createProducerCommonProperties() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(ProducerConfig.SEND_BUFFER_CONFIG, 16384);
        props.put(LINGER_MS_CONFIG, 1);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        return props;
    }

    private static Properties createConsumerCommonProperties() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put(GROUP_ID_CONFIG, "test");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");

        return props;
    }

    private PropertiesFactory() {
    }
}
