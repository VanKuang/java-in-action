package cn.van.kuang.kafka;

import cn.van.kuang.kafka.utils.Utils;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperBootstrap {

    private final static Logger logger = LoggerFactory.getLogger(ZookeeperBootstrap.class);

    public static void main(String[] args) {
        logger.info("===================Starting Zookeeper==========================");

        try {
            QuorumPeerMain.main(new String[]{Utils.getResourcePath() + "zookeeper.properties"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
