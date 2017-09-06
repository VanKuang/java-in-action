package cn.van.kuang.vertx.in.action;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

public class Client {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        NetClientOptions options = new NetClientOptions();

        NetClient client = vertx.createNetClient();

        client.connect(10000, "localhost", result -> {
            if (result.succeeded()) {
                log("Connected");

                NetSocket socket = result.result();

                socket.write("Hi, this is from client");
            } else {
                log("Fail to connect");
            }
        });

    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]--" + message);
    }

}
