package cn.van.kuang.netty5x.server;

import cn.van.kuang.netty5x.server.handler.ConnectionHandler;
import cn.van.kuang.netty5x.server.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.zip.Deflater;

public class NettyServer {

    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final int port;

    private static final DefaultEventExecutorGroup EVENT_EXECUTOR_GROUP = new DefaultEventExecutorGroup(10);

    public NettyServer(int port) {
        this.port = port;
    }

    private void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(ZlibCodecFactory.newZlibDecoder())
                                    .addLast(ZlibCodecFactory.newZlibEncoder(Deflater.BEST_COMPRESSION))
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ConnectionHandler())
                                    .addLast(EVENT_EXECUTOR_GROUP, new MessageHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = serverBootstrap.bind(port).sync();

            logger.info("Netty server started, port: {}", port);

            new Notifier().start();

            future.channel().closeFuture().sync();
        } catch (Throwable throwable) {
            logger.info("Fail to start Netty server", throwable);

            System.exit(-1);
        } finally {
            EVENT_EXECUTOR_GROUP.shutdownGracefully();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(8888);
        nettyServer.start();
    }
}
