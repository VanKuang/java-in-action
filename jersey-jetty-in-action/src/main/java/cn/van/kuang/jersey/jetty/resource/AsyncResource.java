package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        executor.submit((Runnable) () -> {

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignored) {

            }

            response.resume("Long time query Result");

            logger.info("Resume result for id: {}", id);
        });
    }

    @GET
    @Path("timeout")
    public void asyncQueryAndTimeout(@Suspended final AsyncResponse response) {
        response.register((CompletionCallback) throwable -> logger.info("Async callback invoked"));

        response.register((ConnectionCallback) asyncResponse -> logger.info("Connection closed"));

        response.setTimeoutHandler(asyncResponse -> {

            logger.info("Timeout and resume 'service unavailable'");

            response.resume(
                    Response.status(
                            Response.Status.SERVICE_UNAVAILABLE
                    ).build()
            );
        });
        response.setTimeout(3, TimeUnit.SECONDS);

        executor.submit((Runnable) () -> {

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ignored) {

            }

            response.resume("Long time query result");
        });
    }
}
