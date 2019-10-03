package com.van.kuang.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.http.server.HttpServer;

public class RxNetty {

    private final static Logger LOGGER = LoggerFactory.getLogger(RxNetty.class);

    public static void main(String[] args) {
        final DisposableServer server = HttpServer.create()
                .host("localhost")
                .port(9999)
                .route(routers ->
                        routers.get("/", (req, res) -> res.sendString(Mono.just("welcome")))
                                .get("/hello", (req, res) -> res.sendString(Mono.just("hello")))
                                .post("/echo", (req, res) -> res.send(req.receive().retain()))
                                .get("/path/{param}", (req, res) -> res.sendString(Mono.justOrEmpty(req.param("param"))))
                                .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain()))
                )
                .bindNow();

        ///////////CLIENT//////////////////
        final HttpClientResponse response = HttpClient.create()
                .get()
                .uri("http://localhost:9999/hello")
                .response()
                .block();

        LOGGER.info("{}", response.status());
        LOGGER.info("{}", response.toString());

        final String content = HttpClient.create()
                .get()
                .uri("http://localhost:9999/hello")
                .responseContent()
                .aggregate()
                .asString()
                .block();

        LOGGER.info(content);

        final String ping = HttpClient.create()
                .post()
                .uri("http://localhost:9999/echo")
                .send(ByteBufFlux.fromString(Mono.just("ping")))
                .responseContent()
                .aggregate()
                .asString()
                .block();
        LOGGER.info("Sent request to /echo, response body: {}", ping);

        final String path = HttpClient.create()
                .get()
                .uri("http://localhost:9999/path/abc")
                .responseContent()
                .aggregate()
                .asString()
                .block();
        LOGGER.info("Sent request to /path, response body: {}", path);

        HttpClient.create()
                .websocket()
                .uri("ws://localhost:9999/ws")
                .handle((inbound, outbound) -> {
                    inbound.receive()
                            .asString()
                            .take(1)
                            .subscribe(System.out::println);

                    return outbound.sendString(Mono.just("hello"));
                })
                .blockLast();
        ///////////CLIENT//////////////////

        server.disposeNow();
    }

}
