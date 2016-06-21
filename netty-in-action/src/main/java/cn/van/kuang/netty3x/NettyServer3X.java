package cn.van.kuang.netty3x;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
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

public class NettyServer3X {

    private final static Logger logger = LoggerFactory.getLogger(NettyServer3X.class);

    private final int port;
    private HashedWheelTimer wheelTimer = new HashedWheelTimer();

    public NettyServer3X(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        System.setProperty("javax.net.ssl.keyStore", "/Users/VanKuang/Development/workspace/java-in-action/keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "1234@qwer");

        final SSLEngine sslEngine = SSLContext.getDefault().createSSLEngine();
        sslEngine.setUseClientMode(false);

        NioServerSocketChannelFactory channelFactory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

        bootstrap.setPipelineFactory(() -> Channels.pipeline(
                createSslHandler(sslEngine),
                new StringDecoder(),
                new StringEncoder(),
                new MessageHandler()
        ));

        bootstrap.bind(new InetSocketAddress(port));

        logger.info("Started netty server with port [{}]", port);
    }

    private SslHandler createSslHandler(SSLEngine sslEngine) {
        SslHandler sslHandler = new SslHandler(
                sslEngine,
                SslHandler.getDefaultBufferPool(),
                false,
                wheelTimer,
                10000);
        sslHandler.setIssueHandshake(true);
        sslHandler.setCloseOnSSLException(true);
        return sslHandler;
    }

    class MessageHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            logger.info("Received message [{}]", e.getMessage());

            ctx.getChannel().write("PONG");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            logger.info("Caught exception.", e.getCause());
            ctx.getChannel().close();
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            Channel channel = ctx.getChannel();

            logger.info("Channel connected [{}]", channel.getRemoteAddress());
        }

        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            logger.info("Channel disconnected [{}]", ctx.getChannel().getRemoteAddress());
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyServer3X(9999).start();
    }
}
