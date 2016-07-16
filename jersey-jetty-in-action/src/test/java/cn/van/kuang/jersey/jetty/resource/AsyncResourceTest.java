package cn.van.kuang.jersey.jetty.resource;

import cn.van.kuang.jersey.jetty.Constants;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AsyncResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(QueryResource.class);
    }

    @Test
    public void testQuery() {
        Response response = target(Constants.PATH_QUERY + "/html").request().get();
        assertNotNull(response);
        assertEquals(MediaType.TEXT_HTML_TYPE, response.getMediaType());

        String result = response.readEntity(String.class);
        assertNotNull(result);
        assertEquals("<b>PRODUCE AS HTML</b>", result);
    }

}