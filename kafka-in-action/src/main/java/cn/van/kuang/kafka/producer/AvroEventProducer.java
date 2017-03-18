package cn.van.kuang.kafka.producer;

import cn.van.kuang.kafka.avro.Message;
import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class AvroEventProducer {

    public void publish(Message msg) {
        Properties properties = Utils.createAvroProducerProperties();

        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, byte[]> record = new ProducerRecord<>(Constants.AVRO_TOPIC, Utils.toByteArray(msg));

        producer.send(record);

        producer.close();
    }

    public static void main(String[] args) {
        AvroEventProducer producer = new AvroEventProducer();
        producer.publish(Message.newBuilder().setId(1).setContent("test_A").setTimestamp(System.currentTimeMillis()).build());
        producer.publish(Message.newBuilder().setId(2).setContent("test_B").setTimestamp(System.currentTimeMillis()).build());
    }

}
