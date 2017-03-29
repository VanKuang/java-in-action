package cn.van.kuang.netty5x.client;

import cn.van.kuang.common.Heartbeat;
import cn.van.kuang.netty5x.client.request.AsyncRequester;
import cn.van.kuang.netty5x.client.request.SyncRequester;
import cn.van.kuang.netty5x.model.DefaultRequest;
import cn.van.kuang.netty5x.model.Response;
import cn.van.kuang.netty5x.model.TraceableRequest;
import cn.van.kuang.netty5x.pubsub.mapping.Topic;
import cn.van.kuang.netty5x.pubsub.mapping.TopicImpl;
import cn.van.kuang.netty5x.pubsub.pub.Notification;
import cn.van.kuang.netty5x.pubsub.sub.Subscription;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;

public class NettyClient {

    private final static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private final static long DEFAULT_RECONNECT_INTERVAL = 3 * 1000L;
    private final static int DEFAULT_RECONNECT_MAX_TIMES = 10;

    private final String serverAddress;
    private final int port;
    private final int maxRetryTimes;
    private final long retryIntervalInMilSec;

    private final SyncRequester syncRequester = new SyncRequester();
    private final ScheduledExecutorService reconnectService = Executors.newSingleThreadScheduledExecutor();

    private int retryTimes = 0;

    public NettyClient(String serverAddress, int port) {
        this(serverAddress, port, DEFAULT_RECONNECT_MAX_TIMES, DEFAULT_RECONNECT_INTERVAL);
    }

    public NettyClient(String serverAddress, int port, int maxRetryTimes, long retryIntervalInMilSec) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.maxRetryTimes = maxRetryTimes;
        this.retryIntervalInMilSec = retryIntervalInMilSec;
    }

    public void start() {
        logger.info("Starting client");
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(ZlibCodecFactory.newZlibDecoder())
                                    .addLast(ZlibCodecFactory.newZlibEncoder(Deflater.BEST_COMPRESSION))
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new IdleStateHandler(0, 0, 10))
                                    .addLast(new DefaultEventExecutorGroup(10), syncRequester)
                                    .addLast(new ClientHandler());
                        }
                    });

            final ChannelFuture channelFuture = bootstrap.connect(serverAddress, port)
                    .addListener((ChannelFutureListener) future -> {
                        if (!future.isSuccess()) {
                            if (retryTimes > 0) {
                                reconnect();
                            }
                        }
                    }).sync();

            logger.info("Netty client connected to server [{}:{}]", serverAddress, port);

            retryTimes = 0;

            trySyncCall();
            tryAsyncCall();

            channelFuture.channel().closeFuture().sync();
        } catch (Throwable throwable) {
            logger.error("Fail to start netty client, {}", throwable.getMessage());

            if (retryTimes == 0) {
                logger.error("Can't connect server at first moment, will exit now");
                System.exit(-1);
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private void reconnect() {
        if (retryTimes >= maxRetryTimes) {
            logger.error("Retried {} times but all fail, will exit now", retryTimes);
            System.exit(-1);
        }

        logger.info("Try to reconnect in {}ms later, times: {}", DEFAULT_RECONNECT_INTERVAL, retryTimes + 1);

        reconnectService.schedule(() -> {
            retryTimes++;
            start();
        }, retryIntervalInMilSec, TimeUnit.MILLISECONDS);
    }

    private void trySyncCall() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    DefaultRequest request = new DefaultRequest();
                    TraceableRequest traceableRequest = new TraceableRequest(request);
                    Response<String> response = syncRequester.request(traceableRequest);

                    logger.info("Got response synchronous: {}, by {}", response, traceableRequest);
                } catch (Exception e) {
                    logger.error("Fail to get response synchronous", e);
                }
            }
        }).start();
    }

    private void tryAsyncCall() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    DefaultRequest request = new DefaultRequest();
                    TraceableRequest traceableRequest = new TraceableRequest(request);

                    AsyncRequester asyncRequester = new AsyncRequester(syncRequester);
                    Future<Response<Object>> responseFuture = asyncRequester.requestAsync(traceableRequest);
                    Response<Object> asyncResponse = responseFuture.get();

                    logger.info("Got response asynchronous: {}, by {}", asyncResponse, request);
                } catch (Exception e) {
                    logger.error("Fail to get response synchronous", e);
                }
            }

            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException ignore) {
            }
        }).start();
    }

    class ClientHandler extends ChannelHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Channel active, {}", ctx.channel().remoteAddress());

            Subscription subscription = Subscription
                    .Builder
                    .get()
                    .withName("SubscribeTopicALL")
                    .withTopic(new TopicImpl(Topic.Type.ALL))
                    .build();
            ctx.channel().writeAndFlush(subscription);

            logger.info("Wrote [{}]", subscription);

            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Channel inactive, {}", ctx.channel().remoteAddress());
            reconnect();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("Received message, {}", msg);

            if (msg instanceof Notification) {
                @SuppressWarnings("unchecked")
                Notification<String> notification = (Notification<String>) msg;
                notification.getName();
                notification.getTopic();
                notification.getContent();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error("Exception caught, reason: {}", cause.getMessage());
            ctx.close();

            if (cause instanceof IOException) {
                reconnect();
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.ALL_IDLE) {
                    ctx.writeAndFlush(new Heartbeat());
                    logger.debug("Sent heartbeat to server");
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        final NettyClient nettyClient = new NettyClient("127.0.0.1", 8888);
        nettyClient.start();

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }

}
