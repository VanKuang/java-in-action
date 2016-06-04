package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by VanKuang on 16/3/14.
 */
@Singleton
@Path(Constants.PATH_BROADCAST)
public class BroadcastResource {

    private final SseBroadcaster broadcaster = new SseBroadcaster();

    @POST
    @Path("{message}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String broadcastMessage(@PathParam("message") String message) {
        OutboundEvent.Builder builder = new OutboundEvent.Builder();
        OutboundEvent event = builder.name("message")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, message)
                .build();

        broadcaster.broadcast(event);

        return "\"" + message + "\" has been broadcast";
    }

    @GET
    @Path("subscription")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribe() {
        final EventOutput output = new EventOutput();
        broadcaster.add(output);
        return output;
    }

}
