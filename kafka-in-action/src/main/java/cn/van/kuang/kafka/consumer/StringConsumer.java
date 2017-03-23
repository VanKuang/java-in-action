package cn.van.kuang.kafka.consumer;

import cn.van.kuang.kafka.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

import static cn.van.kuang.kafka.utils.PropertiesFactory.createStringConsumerProperties;

public class StringConsumer {

    private final static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    public void start() {
        Properties properties = createStringConsumerProperties();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(Constants.STRING_TOPIC));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Received message, offset: [{}], key: [{}], value: [{}]",
                            record.offset(),
                            record.key(),
                            record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        new StringConsumer().start();
    }
}
