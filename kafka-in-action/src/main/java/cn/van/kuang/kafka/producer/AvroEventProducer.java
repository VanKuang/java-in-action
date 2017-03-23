package cn.van.kuang.kafka.producer;

import cn.van.kuang.kafka.avro.Message;
import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.van.kuang.kafka.utils.PropertiesFactory.createAvroProducerProperties;

public class AvroEventProducer {

    private final static Logger logger = LoggerFactory.getLogger(AvroEventProducer.class);

    private final KafkaProducer<String, byte[]> producer;

    public AvroEventProducer() {
        this.producer = new KafkaProducer<>(createAvroProducerProperties());
    }

    public void close() {
        producer.close();
    }

    public void publish(Message msg) {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(Constants.AVRO_TOPIC, Utils.toByteArray(msg));

        producer.send(record);
    }

    public static void main(String[] args) {
        AvroEventProducer producer = new AvroEventProducer();

        Runtime.getRuntime().addShutdownHook(new Thread(producer::close));

        int id = 1;
        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            producer.publish(Message
                    .newBuilder()
                    .setId(id)
                    .setContent("test_" + id)
                    .setTimestamp(System.currentTimeMillis())
                    .build());

            logger.info("Published event with id=[{}]", id);

            id++;

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
