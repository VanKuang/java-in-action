package cn.van.kuang.jersey.jetty.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CustomizeException extends WebApplicationException {

    public CustomizeException(String message) {
        super(
                Response.status(Response.Status.NOT_FOUND)
                        .entity(message)
                        .type(MediaType.TEXT_PLAIN_TYPE)
                        .build()
        );
    }

}
