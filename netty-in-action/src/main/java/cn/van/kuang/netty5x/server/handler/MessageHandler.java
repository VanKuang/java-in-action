package cn.van.kuang.netty5x.server.handler;

import cn.van.kuang.common.Heartbeat;
import cn.van.kuang.netty5x.model.Response;
import cn.van.kuang.netty5x.model.TraceableRequest;
import cn.van.kuang.netty5x.pubsub.sub.Subscription;
import cn.van.kuang.netty5x.server.util.ChannelHolder;
import cn.van.kuang.netty5x.server.util.TopicChannel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler extends ChannelHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Heartbeat) {
            logger.debug("Received heartbeat: {}", msg);
        } else if (msg instanceof TraceableRequest) {

            logger.info("Received message: {}", msg);

            TraceableRequest traceableRequest = (TraceableRequest) msg;
            ctx.writeAndFlush(new Response<>(traceableRequest.getId(), "Sync Result"));
        } else if (msg instanceof Subscription) {
            logger.info("Received subscription [{}]", msg);

            Subscription subscription = (Subscription) msg;
            TopicChannel topicChannel = new TopicChannel(subscription.getTopics(), ctx.channel());

            ChannelHolder.put(topicChannel);
        }
    }
}
