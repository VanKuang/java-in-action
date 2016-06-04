package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by VanKuang on 16/3/14.
 */
@Path(Constants.PATH_ROOT)
public class RootResource {

    private final static Logger logger = LoggerFactory.getLogger(RootResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String showRootInof(@Context HttpHeaders headers) {
        logger.info("Visited root, client info: User-Agent=[{}], Accpet=[{}]",
                headers.getHeaderString("User-Agent"),
                headers.getHeaderString("Accpet"));

        return "Welcome Jersey hello world";
    }
}
