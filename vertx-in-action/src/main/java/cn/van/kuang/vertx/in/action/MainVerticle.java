package cn.van.kuang.vertx.in.action;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Future<String> dbVerticleDeployment = Future.future();

        vertx.deployVerticle(new DatabaseVerticle(), dbVerticleDeployment.completer());

        dbVerticleDeployment.compose(id -> {
            Future<String> httpServerVerticleDeployment = Future.future();

            vertx.deployVerticle(
                    "cn.van.kuang.vertx.in.action.HttpServerVerticle",
                    new DeploymentOptions().setInstances(2),
                    httpServerVerticleDeployment.completer());

            return httpServerVerticleDeployment;
        }).setHandler(result -> {
            if (result.failed()) {
                startFuture.fail(result.cause());
            } else {
                startFuture.complete();
            }
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle("cn.van.kuang.vertx.in.action.MainVerticle");
    }

}
