package cn.van.kuang.netty3x;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class NettyClient3X {

    private final static Logger logger = LoggerFactory.getLogger(NettyClient3X.class);

    private final static int MAX_WORKER_THREAD_COUNT = 5;
    private final HashedWheelTimer timer = new HashedWheelTimer();
    private ClientBootstrap bootstrap;

    public void start() throws Exception {
        System.setProperty("javax.net.ssl.trustStore", "/Users/VanKuang/Development/workspace/java-in-action/keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234@qwer");

        final SSLEngine sslEngine = SSLContext.getDefault().createSSLEngine();
        sslEngine.setUseClientMode(true);

        NioClientSocketChannelFactory channelFactory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool(),
                MAX_WORKER_THREAD_COUNT);

        bootstrap = new ClientBootstrap(channelFactory);

        bootstrap.setPipelineFactory(() -> Channels.pipeline(
                new SslHandler(sslEngine, SslHandler.getDefaultBufferPool(), false, timer, 10000),
                new StringDecoder(),
                new StringEncoder(),
                new ClientHandler()
        ));

        bootstrap.connect(new InetSocketAddress("localhost", 9999));
    }

    public void stop() {
        if (bootstrap != null) {
            bootstrap.releaseExternalResources();
            bootstrap.shutdown();
        }

        logger.info("STOPPED");
    }

    class ClientHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            logger.info("Received message [{}]", e.getMessage());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            logger.info("Caught exception.", e.getCause());
            ctx.getChannel().close();
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            final Channel channel = ctx.getChannel();

            logger.info("Channel connected [{}]", channel.getRemoteAddress());

            final SslHandler sslHandler = channel.getPipeline().get(SslHandler.class);
            final HandshakeCompleteListener handshakeCompleteListener = new HandshakeCompleteListener(channel);

            ChannelFuture handshakeFuture = sslHandler.handshake();
            handshakeFuture.addListener(handshakeCompleteListener);
        }

        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            logger.info("Channel disconnected [{}]", ctx.getChannel().getRemoteAddress());
        }
    }

    public static void main(String[] args) throws Exception {
        NettyClient3X client = new NettyClient3X();
        client.start();

        Runtime.getRuntime().addShutdownHook(new Thread(client::stop));
    }
}
