package cn.van.kuang.kafka.consumer;

import cn.van.kuang.kafka.avro.Message;
import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class AvroEventConsumer {

    private final static Logger logger = LoggerFactory.getLogger(AvroEventConsumer.class);

    public void start() {
        Properties properties = Utils.createAvroConsumerProperties();

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(Constants.AVRO_TOPIC));

        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);

            for (ConsumerRecord<String, byte[]> record : records) {
                logger.info("Received message, offset: [{}], key: [{}], value: [{}]",
                        record.offset(),
                        record.key(),
                        Utils.byteArrayToDatum(Message.getClassSchema(), record.value()));
            }
        }
    }

    public static void main(String[] args) {
        AvroEventConsumer consumer = new AvroEventConsumer();
        consumer.start();
    }

}
