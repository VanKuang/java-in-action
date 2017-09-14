package cn.van.kuang.vertx.in.action.playground;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class Playground {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.setTimer(1000L, id -> Printer.log("Times up"));

        WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
        executor.executeBlocking(future -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // ignore
            }

            future.complete();
        }, result -> Printer.log("DONE!!!"));

        EventBus eventBus = vertx.eventBus();

        MessageConsumer<String> consumer = eventBus.consumer("notification");

        consumer.completionHandler(result -> {
            if (result.succeeded()) {
                Printer.log("Register consumer handler successful");
            } else {
                Printer.log("Register consumer handler fail");
            }
        });

        consumer.handler(message -> {
            Printer.log("Received message: [" + message.body() + "]");

            message.reply("ACK");
        });

        eventBus.send("notification", "Hello", result -> {
            if (result.succeeded()) {
                Printer.log("Delivered, response: [" + result.result().body() + "]");
            }
        });


    }

}
