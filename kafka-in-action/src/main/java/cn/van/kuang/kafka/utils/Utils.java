package cn.van.kuang.kafka.utils;

import cn.van.kuang.kafka.avro.Message;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.zookeeper.common.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public final class Utils {

    public static String getResourcePath() {
        return System.getProperty("user.dir")
                + File.separator
                + "kafka-in-action"
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator;
    }

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

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(ProducerConfig.SEND_BUFFER_CONFIG, 16384);
        props.put(LINGER_MS_CONFIG, 1);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        return props;
    }

    private static Properties createConsumerCommonProperties() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "test");

        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");

        return props;
    }

    public static byte[] toByteArray(Message message) {
        GenericDatumWriter<Message> writer = new GenericDatumWriter<>(message.getSchema());
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Encoder e = EncoderFactory.get().binaryEncoder(os, null);
            writer.write(message, e);
            e.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T byteArrayToDatum(Schema schema, byte[] byteData) {
        GenericDatumReader<T> reader = new GenericDatumReader<>(schema);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(byteData);
            Decoder decoder = DecoderFactory.get().binaryDecoder(byteArrayInputStream, null);
            return reader.read(null, decoder);
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeStream(byteArrayInputStream);
        }
    }


    public static void main(String[] args) {
        System.out.println(getResourcePath());
    }

    private Utils() {
    }

}
