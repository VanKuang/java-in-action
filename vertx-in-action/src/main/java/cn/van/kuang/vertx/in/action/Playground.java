package cn.van.kuang.vertx.in.action;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.net.NetServer;

public class Playground {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.setTimer(1000L, id -> log("Times up"));

        WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
        executor.executeBlocking(future -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // ignore
            }

            future.complete();
        }, result -> log("DONE!!!"));

        EventBus eventBus = vertx.eventBus();

        MessageConsumer<String> consumer = eventBus.consumer("notification");

        consumer.completionHandler(result -> {
            if (result.succeeded()) {
                log("Register consumer handler successful");
            } else {
                log("Register consumer handler fail");
            }
        });

        consumer.handler(message -> {
            log("Received message: [" + message.body() + "]");

            message.reply("ACK");
        });

        eventBus.send("notification", "Hello", result -> {
            if (result.succeeded()) {
                log("Delivered, response: [" + result.result().body() + "]");
            }
        });

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
                log("Fail to bind port");
            }
        });
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]--" + message);
    }

}
