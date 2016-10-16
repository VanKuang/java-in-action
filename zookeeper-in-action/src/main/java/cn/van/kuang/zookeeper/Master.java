package cn.van.kuang.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class Master implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(Master.class);

    private final String hostWithPort;

    private ZooKeeper zooKeeper;

    public Master(String hostWithPort) {
        this.hostWithPort = hostWithPort;
    }

    public void start() throws IOException {
        logger.info("Going to start ZooKeeper");
        zooKeeper = new ZooKeeper(hostWithPort, 15000, this);
    }

    public void active() throws KeeperException, InterruptedException {
        String serverId = Integer.toHexString(new Random().nextInt());
        zooKeeper.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
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
