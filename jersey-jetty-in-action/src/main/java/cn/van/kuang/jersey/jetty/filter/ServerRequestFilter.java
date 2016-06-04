package cn.van.kuang.jersey.jetty.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class ServerRequestFilter implements ContainerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(ServerRequestFilter.class);

    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.info("Request URI: [{}], Action: [{}]",
                requestContext.getUriInfo().getPath(),
                requestContext.getMethod());

        requestContext.getHeaders().add("TestHeaderKey", "TestHeaderValue");

        if (requestContext.getUriInfo().getPath().contains("admin")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Not Allow")
                            .build()
            );
        }
    }
}
