package cn.van.kuang.jersey.jetty;

import cn.van.kuang.jersey.jetty.filter.ServerRequestFilter;
import cn.van.kuang.jersey.jetty.filter.ServerResponseFilter;
import cn.van.kuang.jersey.jetty.interceptor.CompressInterceptor;
import cn.van.kuang.jersey.jetty.interceptor.DynamicBinding;
import cn.van.kuang.jersey.jetty.interceptor.ServerWriterInterceptor;
import cn.van.kuang.jersey.jetty.listerner.ApplicationListenerImpl;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JerseyServer {

    private final static Logger logger = LoggerFactory.getLogger(JerseyServer.class);

    public void start() {
        logger.info("Starting Jersey server");

        try {
            Server server = createServer();
            server.start();

            logger.info("Started Jersey server, [{}:{}]",
                    Constants.HOST,
                    Constants.PORT);
        } catch (Throwable throwable) {
            logger.error("Fail to start Jersey server", throwable);

            System.exit(-1);
        }
    }

    private Server createServer() {
        final URI uri = UriBuilder.fromUri(Constants.HOST).port(Constants.PORT).build();

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
        resourceConfig.property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);

        resourceConfig.packages("cn.van.kuang.jersey.jetty.resource");

        resourceConfig.register(new ApplicationListenerImpl());

        resourceConfig.register(new ServerRequestFilter());
        resourceConfig.register(new ServerResponseFilter());

        resourceConfig.register(new ServerWriterInterceptor());
        resourceConfig.register(new CompressInterceptor());
        resourceConfig.register(new DynamicBinding());

        return JettyHttpContainerFactory.createServer(uri, resourceConfig, false);
    }

    public static void main(String[] args) {
        new JerseyServer().start();
    }
}
