package cn.van.kuang.kafka.producer;

import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class StringProducer {

    public void publish(String msg) {
        Properties properties = Utils.createStringProducerProperties();

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>(Constants.STRING_TOPIC, msg);

        producer.send(record);

        producer.close();
    }

    public static void main(String[] args) {
        StringProducer producer = new StringProducer();
        producer.publish("Hi Kafka");
        producer.publish("Hi Van");
    }
}
