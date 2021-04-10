package cn.van.kuang.jersey.jetty;

import cn.van.kuang.jersey.jetty.filter.ServerRequestFilter;
import cn.van.kuang.jersey.jetty.filter.ServerResponseFilter;
import cn.van.kuang.jersey.jetty.interceptor.CompressInterceptor;
import cn.van.kuang.jersey.jetty.interceptor.DynamicBinding;
import cn.van.kuang.jersey.jetty.interceptor.ServerWriterInterceptor;
import cn.van.kuang.jersey.jetty.listerner.ApplicationListenerImpl;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.process.JerseyProcessingUncaughtExceptionHandler;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

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

        EncodingFilter.enableFor(resourceConfig, GZipEncoder.class);

        return createJettyServer(resourceConfig);
    }

    private Server createJettyServer(ResourceConfig resourceConfig) {
        final Server server = new Server(new JettyConnectorThreadPool());

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(Constants.SSL_PORT);

        final ServerConnector http = new ServerConnector(
                server,
                new HttpConnectionFactory(httpConfig));
        http.setPort(Constants.PORT);

        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        final SslContextFactory sslContextFactory = createSslContextFactory();

        final ServerConnector https = new ServerConnector(
                server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(httpsConfig));
        https.setPort(Constants.SSL_PORT);

        server.setConnectors(new Connector[]{http, https});

        server.setHandler(ContainerFactory.createContainer(JettyHttpContainer.class, resourceConfig));

        return server;
    }

    private SslContextFactory createSslContextFactory() {
        SslContextFactory factory = new SslContextFactory();
        factory.setKeyStorePath("/Users/VanKuang/Development/workspace/java-in-action/java-core/src/main/resources/keystore");
        factory.setKeyStorePassword("*******");
        factory.setKeyManagerPassword("*******");
        return factory;
    }

    private static final class JettyConnectorThreadPool extends QueuedThreadPool {
        private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("jetty-http-server-%d")
                .setUncaughtExceptionHandler(new JerseyProcessingUncaughtExceptionHandler())
                .build();

        @Override
        protected Thread newThread(Runnable runnable) {
            return threadFactory.newThread(runnable);
        }
    }

    public static void main(String[] args) {
        new JerseyServer().start();
    }
}
