package cn.van.kuang.kafka.consumer;

import cn.van.kuang.kafka.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.van.kuang.kafka.utils.PropertiesFactory.createStringConsumerProperties;

public class StandAloneConsumer {

    private final static Logger logger = LoggerFactory.getLogger(StandAloneConsumer.class);

    private final KafkaConsumer<String, String> consumer;
    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public StandAloneConsumer() {
        this.consumer = new KafkaConsumer<>(createStringConsumerProperties());
    }

    public void start() {
        String topic = Constants.STRING_TOPIC;

        List<PartitionInfo> partitionInfoList = consumer.partitionsFor(topic);

        if (partitionInfoList != null) {
            List<TopicPartition> topicPartitionList = new ArrayList<>(partitionInfoList.size());
            for (PartitionInfo partitionInfo : partitionInfoList) {
                topicPartitionList.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            }
            consumer.assign(topicPartitionList);

            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(100);

                    for (ConsumerRecord<String, String> record : records) {
                        logger.info("Received message, offset: [{}], key: [{}], value: [{}]",
                                record.offset(),
                                record.key(),
                                record.value());

                        currentOffsets.put(
                                new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset()));
                    }

                    consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                        if (exception != null) {
                            logger.error("Offset commit failed.", exception);
                        }
                    });
                }
            } catch (WakeupException e) {
                // ignore for shutdown
            } finally {
                try {
                    consumer.commitSync(currentOffsets);
                } finally {
                    consumer.close();
                    logger.info("Consumer closed");
                }
            }
        } else {
            logger.warn("No topic: {}", topic);
        }


    }

    public static void main(String[] args) {
        StandAloneConsumer consumer = new StandAloneConsumer();
        consumer.start();
    }

}
