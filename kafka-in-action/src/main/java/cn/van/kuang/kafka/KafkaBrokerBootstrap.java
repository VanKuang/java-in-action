package cn.van.kuang.kafka;

import cn.van.kuang.kafka.utils.Utils;
import kafka.Kafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaBrokerBootstrap {

    private final static Logger logger = LoggerFactory.getLogger(KafkaBrokerBootstrap.class);

    public static void main(String[] args) {
        logger.info("===================Starting Kafka Broker==========================");

        try {
            Kafka.main(new String[]{Utils.getResourcePath() + "server.properties"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
