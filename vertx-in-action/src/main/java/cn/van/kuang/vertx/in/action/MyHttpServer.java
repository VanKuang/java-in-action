package cn.van.kuang.vertx.in.action;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

import static cn.van.kuang.vertx.in.action.Printer.log;

public class MyHttpServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            MultiMap headers = request.headers();

            log("Headers -> " + headers);

            log("URI -> " + request.uri());

            log("Path -> " + request.path());

            request.bodyHandler(buffer -> {
                log("Received -> " + buffer.toString());

                request.response().setChunked(true).write("hi hi").end();
            });
        });

        server.listen(9999, "localhost", result -> {
            if (result.succeeded()) {
                log("Listen to port 9999");
            } else {
                log("Fail to listen port 9999");

                result.cause().printStackTrace();
            }
        });
    }
}
