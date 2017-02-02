package cn.van.kuang.redis;

import redis.clients.jedis.Jedis;

public class Application {

    public static void main(String[] args) {
        Jedis client = new Jedis("localhost", 6379);

        client.set("1", "van");

        System.out.println(client.get("1"));
    }

}