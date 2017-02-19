package cn.van.kuang.jersey.jetty;

import cn.van.kuang.jersey.jetty.interceptor.ClientReaderInterceptor;
import cn.van.kuang.jersey.jetty.response.BigObject;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JerseyClient {

    private final static Logger logger = LoggerFactory.getLogger(JerseyClient.class);

    private final String ADDRESS = Constants.HOST + ":" + Constants.PORT;
    private final WebTarget target = ClientBuilder.newClient().target(ADDRESS);

    private void requestHTML() {
        String html = target.path(Constants.PATH_QUERY)
                .path("html")
                .request(MediaType.TEXT_HTML_TYPE)
                .get(String.class);

        logger.info(html);
    }

    private void requestBigObject() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new ClientReaderInterceptor());

        BigObject bigObject = ClientBuilder.newClient(clientConfig)
                .target(ADDRESS)
                .path(Constants.PATH_QUERY)
                .path("bigObject")
                .request()
                .get(BigObject.class);

        logger.info("{}", bigObject);
    }

    private void requestCompressedString() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new ClientReaderInterceptor());

        String compression = ClientBuilder.newClient(clientConfig)
                .target(ADDRESS)
                .path(Constants.PATH_QUERY)
                .path("compression")
                .request()
                .get(String.class);

        logger.info(compression);
    }

    private void requestDynamicBindng() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new ClientReaderInterceptor());

        String value = ClientBuilder.newClient(clientConfig)
                .target(ADDRESS)
                .path(Constants.PATH_QUERY)
                .path("dynamicBinding")
                .request()
                .get(String.class);

        logger.info(value);
    }

    private void requestAsyncResult() throws Exception {
        logger.info("Start Asyn request");
        String result = target.path(Constants.PATH_ASYNC)
                .path("query")
                .request()
                .async()
                .get()
                .get()
                .readEntity(String.class);
        logger.info("Async result: " + result);

        logger.info("Start Asyn request with callback");
        target.path(Constants.PATH_ASYNC)
                .path("query")
                .request()
                .async()
                .get(new InvocationCallback<String>() {
                    public void completed(String s) {
                        logger.info("Async query get response, complete() invoked, result: " + s);
                    }

                    public void failed(Throwable throwable) {
                        logger.info("Fail to get result async");
                        throwable.printStackTrace();
                    }
                });
    }

    private void readChunk() {
        Response response = target.path(Constants.PATH_EVENTS)
                .path("chunk")
                .request()
                .get();

        ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>() {
        });

        String chunk;
        while ((chunk = chunkedInput.read()) != null) {
            logger.info(chunk);
        }
    }

    private void post() {
        target.path(Constants.PATH_UPDATE)
                .path("resources")
                .request()
                .post(Entity.text("POST_ID_123"));
    }

    private void put() {
        target.path(Constants.PATH_UPDATE)
                .path("resources")
                .request()
                .put(Entity.text("PUT_ID_123"));
    }

    private void delete() {
        target.path(Constants.PATH_UPDATE)
                .path("resources")
                .path("DELETE_ID_123,DELETE_ID_456,DELETE_ID_789")
                .request()
                .delete();
    }

    private void subscribe() {
        logger.info("Subscribe synchronous");

        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
        WebTarget target = client.target(ADDRESS)
                .path(Constants.PATH_BROADCAST)
                .path("subscription");

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();

            if (inboundEvent == null) {
                break;
            }

            logger.info("Received event by synchronous: " + inboundEvent.readData(String.class));
        }
    }

    private synchronized void subscribeAsynchronous() {
        logger.info("Susbscribe asynchronous");

        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
        WebTarget target = client
                .target(ADDRESS)
                .path(Constants.PATH_BROADCAST)
                .path("subscription");

        EventSource source = EventSource.target(target).named("asyncSubscriber").open();
        source.register(
                inboundEvent -> logger.info("Received event by asynchronous: " + inboundEvent.readData(String.class)));
    }

    public static void main(String[] args) throws Exception {
        JerseyClient jerseyClient = new JerseyClient();

        jerseyClient.requestHTML();
        jerseyClient.requestBigObject();
        jerseyClient.requestCompressedString();
        jerseyClient.requestDynamicBindng();
        jerseyClient.requestAsyncResult();

        jerseyClient.readChunk();

        jerseyClient.post();
        jerseyClient.put();
        jerseyClient.delete();

        jerseyClient.subscribe();
        jerseyClient.subscribeAsynchronous();

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }
}
