package cn.van.kuang.kafka.consumer;

import cn.van.kuang.kafka.avro.Message;
import cn.van.kuang.kafka.utils.Constants;
import cn.van.kuang.kafka.utils.Utils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.van.kuang.kafka.utils.PropertiesFactory.createAvroConsumerProperties;

public class AvroEventConsumer {

    private final static Logger logger = LoggerFactory.getLogger(AvroEventConsumer.class);

    private final KafkaConsumer<String, byte[]> consumer;
    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public AvroEventConsumer() {
        this.consumer = new KafkaConsumer<>(createAvroConsumerProperties());
    }

    public void start() {
        consumer.subscribe(Collections.singletonList(Constants.AVRO_TOPIC), new ConsumerRebalanceListenerImpl());

        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(100);

                for (ConsumerRecord<String, byte[]> record : records) {
                    logger.info("Received message, offset: [{}], key: [{}], value: [{}]",
                            record.offset(),
                            record.key(),
                            Utils.byteArrayToDatum(Message.getClassSchema(), record.value()));

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

    }

    class ConsumerRebalanceListenerImpl implements ConsumerRebalanceListener {
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            logger.info("Partitions revoked: {}", partitions);

            consumer.commitSync(currentOffsets);
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            logger.info("Partitions assigned: {}", partitions);
        }
    }

    public static void main(String[] args) {
        AvroEventConsumer consumer = new AvroEventConsumer();

        Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Exiting.....");

            consumer.consumer.wakeup();

            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        consumer.start();
    }

}
