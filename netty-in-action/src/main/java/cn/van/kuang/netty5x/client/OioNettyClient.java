package cn.van.kuang.netty5x.client;

import cn.van.kuang.netty5x.model.DefaultRequest;
import cn.van.kuang.netty5x.model.Response;
import cn.van.kuang.netty5x.model.TraceableRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

public class OioNettyClient {

    private final static Logger logger = LoggerFactory.getLogger(OioNettyClient.class);

    private final String serverAddress;
    private final int port;

    private volatile Response response;

    public OioNettyClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void start() {
        logger.info("Starting client");
        EventLoopGroup workerGroup = new OioEventLoopGroup();

        try {
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(OioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new JdkZlibDecoder())
                                    .addLast(new JdkZlibEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            final ChannelFuture channelFuture = bootstrap.connect(serverAddress, port).sync();

            logger.info("Netty client connected to server [{}:{}]", serverAddress, port);

            doTryRequest(channelFuture.channel());

            channelFuture.channel().closeFuture().sync();
        } catch (Throwable throwable) {
            logger.error("Fail to start netty client, {}", throwable.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private void doTryRequest(final Channel context) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        DefaultRequest request = new DefaultRequest();
                        TraceableRequest traceableRequest = new TraceableRequest(request);
                        context.writeAndFlush(traceableRequest);

                        logger.info("Got response synchronous: {}, by {}", getResponse(), traceableRequest);
                    } catch (Exception e) {
                        logger.error("Fail to get response synchronous", e);
                    }
                }
            }
        };
        t.setName("Blocking-Call-Thread");
        t.start();
    }

    private Response<String> getResponse() throws Exception {
        long start = System.currentTimeMillis();
        while (response == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignore) {
            }

            if (System.currentTimeMillis() - start >= 10 * 1000) {
                throw new TimeoutException("Timeout to wait response");
            }
        }

        Response<String> tmpResponse = new Response<String>(response.getRequestId(), (String) response.getContent());

        response = null;

        return tmpResponse;
    }

    class ClientHandler extends ChannelHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Channel active, {}", ctx.channel().remoteAddress());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Channel inactive, {}", ctx.channel().remoteAddress());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.debug("Received message, {}", msg);
            response = (Response) msg;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error("Exception caught, reason: {}", cause.getMessage());
            ctx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        final OioNettyClient nettyClient = new OioNettyClient("127.0.0.1", 8888);
        nettyClient.start();

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }

}
