package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by VanKuang on 16/3/14.
 */
@Path(Constants.PATH_ASYNC)
@Produces(MediaType.TEXT_PLAIN)
public class AsyncResource {

    private final static Logger logger = LoggerFactory.getLogger(AsyncResource.class);

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @GET
    @Path("query")
    public void asyncQuery(@Suspended final AsyncResponse response) {
        final long id = System.currentTimeMillis();

        logger.info("Received request, id: {}", id);

        executor.submit(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {

                }

                response.resume("Long time query Result");

                logger.info("Resume result for id: {}", id);
            }
        });
    }

    @GET
    @Path("timeout")
    public void asyncQueryAndTimeout(@Suspended final AsyncResponse response) {
        response.register(new CompletionCallback() {
            public void onComplete(Throwable throwable) {
                logger.info("Async callback invoked");
            }
        });

        response.register(new ConnectionCallback() {
            public void onDisconnect(AsyncResponse asyncResponse) {
                logger.info("Connection closed");
            }
        });

        response.setTimeoutHandler(new TimeoutHandler() {
            public void handleTimeout(AsyncResponse asyncResponse) {

                logger.info("Timeout and resume 'service unavailable'");

                response.resume(
                        Response.status(
                                Response.Status.SERVICE_UNAVAILABLE
                        ).build()
                );
            }
        });
        response.setTimeout(3, TimeUnit.SECONDS);

        executor.submit(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException ignored) {

                }

                response.resume("Long time query result");
            }
        });
    }
}
