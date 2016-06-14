package cn.van.kuang.jersey.jetty.interceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class ClientReaderInterceptor implements ReaderInterceptor {

    public Object aroundReadFrom(ReaderInterceptorContext readerContext)
            throws IOException, WebApplicationException {
        final InputStream inputStream = readerContext.getInputStream();
        readerContext.setInputStream(new GZIPInputStream(inputStream));
        return readerContext.proceed();
    }
}
