package cn.van.kuang.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;

public class ClusterApplication {

    public static void main(String[] args) {
        HashSet<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort("localhost", 30001));
        clusterNodes.add(new HostAndPort("localhost", 30002));
        clusterNodes.add(new HostAndPort("localhost", 30003));
        clusterNodes.add(new HostAndPort("localhost", 30004));
        clusterNodes.add(new HostAndPort("localhost", 30005));
        clusterNodes.add(new HostAndPort("localhost", 30006));
        JedisCluster clusters = new JedisCluster(clusterNodes);

        String KEY_PLAYER = "player";

        clusters.lpush(KEY_PLAYER, "Kobe");
        clusters.lpush(KEY_PLAYER, "James");
        clusters.lpush(KEY_PLAYER, "Curry");

        List<String> players = clusters.lrange(KEY_PLAYER, 0, 2);

        for (String player : players) {
            System.out.println(player);
        }
    }

}
