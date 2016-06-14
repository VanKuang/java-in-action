package cn.van.kuang.jersey.jetty.interceptor;

import cn.van.kuang.jersey.jetty.annotation.Compress;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

@Compress
public class CompressInterceptor implements WriterInterceptor {

    public void aroundWriteTo(WriterInterceptorContext writerContext)
            throws IOException, WebApplicationException {
        final OutputStream outputStream = writerContext.getOutputStream();
        writerContext.setOutputStream(new GZIPOutputStream(outputStream));
        writerContext.proceed();
    }

}
