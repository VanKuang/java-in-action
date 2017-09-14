package cn.van.kuang.vertx.in.action.playground;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

public class MyNetServer {

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();

        server.connectHandler(socket -> socket.handler(
                buffer -> {
                    Printer.log("Received some bytes, length=" + buffer.length() + ", msg: " + buffer.toString());
                })
        );

        server.listen(10000, "localhost", result -> {
            if (result.succeeded()) {
                Printer.log("Listened port: [" + server.actualPort() + "]");
            } else {
                Printer.log("Fail to bind port 10000");

                result.cause().printStackTrace();
            }
        });
    }
}
