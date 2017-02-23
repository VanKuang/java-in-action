package cn.van.kuang.kafka;

import kafka.Kafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaBootstrap {

    private final static Logger logger = LoggerFactory.getLogger(KafkaBootstrap.class);

    public static void main(String[] args) {
        logger.info("===================Starting Kafka==========================");

        try {
            Kafka.main(new String[]{Utils.getResourcePath() + "server.properties"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
