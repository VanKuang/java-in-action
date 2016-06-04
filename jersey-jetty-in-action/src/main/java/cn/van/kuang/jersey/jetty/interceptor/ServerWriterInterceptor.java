package cn.van.kuang.jersey.jetty.interceptor;

import cn.van.kuang.jersey.jetty.response.BigObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by VanKuang on 16/3/14.
 */
public class ServerWriterInterceptor implements WriterInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(ServerWriterInterceptor.class);

    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        if (context.getEntity() instanceof BigObject) {
            logger.info("Zip big object");

            final OutputStream outputStream = context.getOutputStream();
            context.setOutputStream(new GZIPOutputStream(outputStream));
        }
        context.proceed();
    }
}
