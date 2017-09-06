package cn.van.kuang.vertx.in.action;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

import static cn.van.kuang.vertx.in.action.Printer.log;

public class MyNetServer {

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();

        server.connectHandler(socket -> socket.handler(
                buffer -> {
                    log("Received some bytes, length=" + buffer.length() + ", msg: " + buffer.toString());
                })
        );

        server.listen(10000, "localhost", result -> {
            if (result.succeeded()) {
                log("Listened port: [" + server.actualPort() + "]");
            } else {
                log("Fail to bind port 10000");

                result.cause().printStackTrace();
            }
        });
    }
}
