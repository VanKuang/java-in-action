package cn.van.kuang.vertx.in.action.playground;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class MyHttpServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            MultiMap headers = request.headers();

            Printer.log("Headers -> " + headers);

            Printer.log("URI -> " + request.uri());

            Printer.log("Path -> " + request.path());

            request.bodyHandler(buffer -> {
                Printer.log("Received -> " + buffer.toString());

                request.response().setChunked(true).write("hi hi").end();
            });
        });

        server.listen(9999, "localhost", result -> {
            if (result.succeeded()) {
                Printer.log("Listen to port 9999");
            } else {
                Printer.log("Fail to listen port 9999");

                result.cause().printStackTrace();
            }
        });
    }
}
