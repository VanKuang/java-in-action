package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import cn.van.kuang.jersey.jetty.annotation.Compress;
import cn.van.kuang.jersey.jetty.exception.CustomizeException;
import cn.van.kuang.jersey.jetty.param.JobInfo;
import cn.van.kuang.jersey.jetty.response.BigObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(Constants.PATH_QUERY)
public class QueryResource {

    private final static Logger logger = LoggerFactory.getLogger(QueryResource.class);

    @GET
    @Path("html")
    @Produces(MediaType.TEXT_HTML)
    public String productHtml() {
        return "<b>PRODUCE AS HTML</b>";
    }

    @GET
    @Path("company/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryWithPathParam(
            @DefaultValue("1") @PathParam("id") int id) {
        logger.info("Received query request, param: [id={}]",
                new Object[]{id});

        return "Query with param: [id=" + id + "]";
    }

    @GET
    @Path("staff")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryWithQueryParam(
            @DefaultValue("HSBC,SSE") @QueryParam("job") JobInfo jobInfo) {
        logger.info("Received query request, param: [JobInfo={}]",
                new Object[]{
                        jobInfo
                });

        return "Query with param: [JobInfo=" + jobInfo + "]";
    }

    @GET
    @Path("URI")
    @Produces(MediaType.TEXT_PLAIN)
    public Response retrieveUriInfo(@Context UriInfo uriInfo) {
        return Response.ok("Path: " + uriInfo.getPath(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @GET
    @Path("exception")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryWithException() {
        throw new CustomizeException("Exception");
    }

    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject queryJson() {
        return Json.createObjectBuilder().add("ID", 1).add("Name", "Van").build();
    }

    @GET
    @Path("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String admin() {
        return "admin";
    }

    @GET
    @Path("bigObject")
    @Produces(MediaType.APPLICATION_XML)
    public BigObject getBigObject() {
        return new BigObject("Very big object");
    }

    @GET
    @Path("compression")
    @Produces(MediaType.TEXT_PLAIN)
    @Compress
    public String compress() {
        return "Compress Text";
    }

    @GET
    @Path("dynamicBinding")
    @Produces(MediaType.TEXT_PLAIN)
    public String dynamicBinding() {
        return "Dynamic Binding";
    }
}
