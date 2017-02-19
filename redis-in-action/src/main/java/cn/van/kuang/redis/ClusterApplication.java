package cn.van.kuang.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;

public class ClusterApplication {

    public static void main(String[] args) throws Exception {
        HashSet<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort("localhost", 30001));
        clusterNodes.add(new HostAndPort("localhost", 30002));
        clusterNodes.add(new HostAndPort("localhost", 30003));
        clusterNodes.add(new HostAndPort("localhost", 30004));
        clusterNodes.add(new HostAndPort("localhost", 30005));
        clusterNodes.add(new HostAndPort("localhost", 30006));

        JedisCluster cluster = new JedisCluster(clusterNodes);

        String KEY_PLAYER = "player";

        System.out.println("Exists: " + cluster.exists(KEY_PLAYER));

        if (cluster.exists(KEY_PLAYER)) {
            cluster.del(KEY_PLAYER);
        }

        cluster.lpush(KEY_PLAYER, "Kobe");
        cluster.lpush(KEY_PLAYER, "James");
        cluster.lpush(KEY_PLAYER, "Curry");

        Long length = cluster.llen(KEY_PLAYER);

        System.out.println("Length: " + length);

        List<String> players = cluster.lrange(KEY_PLAYER, 0, length - 1);

        for (String player : players) {
            System.out.println(player);
        }

        cluster.close();
    }

}
