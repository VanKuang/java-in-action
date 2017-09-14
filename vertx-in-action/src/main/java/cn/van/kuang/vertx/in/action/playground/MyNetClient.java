package cn.van.kuang.vertx.in.action.playground;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class MyNetClient {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        NetClient client = vertx.createNetClient();

        client.connect(10000, "localhost", result -> {
            if (result.succeeded()) {
                Printer.log("Connected");

                NetSocket socket = result.result();

                socket.write("Hi, this is from client");
            } else {
                Printer.log("Fail to connect");
            }
        });

    }

}
