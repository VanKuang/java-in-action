package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

@Path(Constants.PATH_UPDATE)
public class UpdateResource {

    private final static Logger logger = LoggerFactory.getLogger(UpdateResource.class);

    @Path("resources")
    @POST
    public void post(String id) {
        logger.info("post({}) invoked", id);
    }

    @Path("resources")
    @PUT
    public void put(String id) {
        logger.info("put({}) invoked", id);
    }

    @Path("resources/{id}")
    @DELETE
    public void delete(@PathParam("id") String id) {
        logger.info("delete({}) invoked", id);
    }

}
