package cn.van.kuang.kafka.consumer;

import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class StringConsumer {

    private final static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    public void start() {
        Properties properties = Utils.createStringConsumerProperties();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(Constants.STRING_TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                logger.info("Received message, offset: [{}], key: [{}], value: [{}]",
                        record.offset(),
                        record.key(),
                        record.value());
            }
        }
    }

    public static void main(String[] args) {
        new StringConsumer().start();
    }
}
