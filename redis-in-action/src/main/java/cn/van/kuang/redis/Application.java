package cn.van.kuang.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class Application {

    public static void main(String[] args) {
        Jedis client = new Jedis("localhost", 6379);

        String key1 = "KEY_1";
        String key2 = "KEY_2";
        String LIST_KEY = "LIST_KEY";

        String reply = client.set(key1, "KOBE");

        System.out.println("SET KEY_1 WITH VALUE \"KOBE\" GET REPLY:" + reply);

        System.out.println("SET KEY_2 WITH VALUE \"JAMES\"");
        client.set(key2, "JAMES");

        System.out.println("EXIST KEY_1: " + client.exists(key1));
        System.out.println("EXIST KEY_1 & KEY_2: " + client.exists(key1, key2));
        System.out.println("EXIST KEY_1 & KEY_2 & KEY_3: " + client.exists(key1, key2, "KEY_3"));

        System.out.println("GET KEY_1: " + client.get(key1));

        System.out.println("DELETE KEY_2: " + client.del(key2));

        System.out.println("GET KEY_2: " + client.get(key2));

        System.out.println("TYPE OF KEY_1: " + client.type(key1));

        client.lpush(LIST_KEY, "KB", "LBJ", "KD");

        Set<String> keys = client.keys("KEY*");
        System.out.println("KEYS start with KEY");
        for (String key : keys) {
            System.out.println(key);
        }

        System.out.println("RANDOM KEY: " + client.randomKey());

        System.out.println("TTL KEY_1: " + client.ttl(key1));
        System.out.println("TTL KEY_2: " + client.ttl(key2));

        System.out.println("SET KEY_2 WITH VALUE \"JAMES\"");
        client.set(key2, "JAMES");

        System.out.println("MGET: " + client.mget(key1, key2));
    }

}