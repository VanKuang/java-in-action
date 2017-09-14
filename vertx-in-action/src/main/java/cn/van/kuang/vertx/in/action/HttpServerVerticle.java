package cn.van.kuang.vertx.in.action;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.van.kuang.vertx.in.action.Constants.*;

public class HttpServerVerticle extends AbstractVerticle {

    private final static Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    private static final FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/").handler(this::indexHandler);

        server.requestHandler(router::accept)
                .listen(8080, ar -> {
                            if (ar.failed()) {
                                logger.error("Fail to start http server", ar.cause());
                                startFuture.fail(ar.cause());
                            } else {
                                logger.info("Http server started on port 8080");
                                startFuture.complete();
                            }
                        }
                );
    }

    private void indexHandler(RoutingContext context) {

        String queue = config().getString(EVENT_BUS_REQUEST_QUEUE, EVENT_BUS_REQUEST_QUEUE);

        DeliveryOptions deliveryOptions = new DeliveryOptions().addHeader(HEADER_ACTION, ACTION_LIST_ALL);

        vertx.eventBus().send(queue, new JsonObject(), deliveryOptions, reply -> {
            if (reply.succeeded()) {
                JsonObject body = (JsonObject) reply.result().body();

                context.put("title", "Welcome to NBA");
                context.put("players", body.getJsonArray("players").getList());

                templateEngine.render(context, "templates", "/index.ftl", ar -> {
                    if (ar.succeeded()) {
                        context.response().putHeader("Content-type", "text/html");
                        context.response().end(ar.result());
                    } else {
                        context.fail(ar.cause());
                    }
                });
            } else {
                context.fail(reply.cause());
            }
        });

    }

}
