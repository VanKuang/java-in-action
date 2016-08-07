package cn.van.kuang.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Master implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(Master.class);

    private final String hostWithPort;

    public Master(String hostWithPort) {
        this.hostWithPort = hostWithPort;
    }

    public void start() throws IOException {
        logger.info("Going to start ZooKeeper");
        new ZooKeeper(hostWithPort, 15000, this);
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("On process, received {}", event);
    }

    public static void main(String[] args) throws Exception {
        Master master = new Master("127.0.0.1:2181");

        master.start();

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }
}
