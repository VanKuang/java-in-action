package cn.van.kuang.vertx.in.action;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

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
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]--" + message);
    }

}
