package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ChunkedOutput;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;

@Path(Constants.PATH_EVENTS)
public class ServerSentEventResource {

    @GET
    @Path("chunk")
    @Produces(MediaType.TEXT_PLAIN)
    public ChunkedOutput<String> queryChunk() {
        final ChunkedOutput<String> output = new ChunkedOutput<>(String.class, "\r\n");

        new Thread(() -> {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File("/Users/VanKuang/Development/workspace/java-in-action/jersey-jetty-in-action/src/main/java/cn/van/jersey/jetty/resource/QueryResource.java"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

                String line = bufferedReader.readLine();
                while (line != null) {
                    Thread.sleep(50L);

                    output.write(line);

                    line = bufferedReader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return output;
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getEvents() {
        final EventOutput eventOutput = new EventOutput();

        new Thread(() -> {
            try {
                for (int i = 0, length = 10; i < length; i++) {
                    final OutboundEvent.Builder builder = new OutboundEvent.Builder();
                    builder.name("message-to-client");
                    builder.data(String.class, "Message " + i);

                    final OutboundEvent outboundEvent = builder.build();
                    eventOutput.write(outboundEvent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    eventOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return eventOutput;
    }

}
