package cn.van.kuang.vertx.in.action;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertxHttpClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(VertxHttpClient.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        WebClient.create(vertx)
                .get(80, "www.baidu.com", "/")
                .send(ar -> {
                    if (ar.succeeded()) {
                        LOGGER.info("{}", ar.result().statusCode());
                        LOGGER.info("{}", ar.result().bodyAsString().substring(0, 10) + "...");
                    } else {
                        LOGGER.error("Fail", ar.cause());
                    }
                });
    }

}
