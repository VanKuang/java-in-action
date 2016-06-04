package cn.van.kuang.netty5x.server.handler;

import cn.van.kuang.netty5x.server.util.ChannelHolder;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler extends ChannelHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Channel active, {}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Channel inactive, {}", ctx.channel().remoteAddress());

        ChannelHolder.remove(ctx.channel().id());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Exception caught, reason: {}", cause.getMessage());
        ctx.close();
    }

}