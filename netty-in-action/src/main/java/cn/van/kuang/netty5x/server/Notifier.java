package cn.van.kuang.netty5x.server;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;
import cn.van.kuang.netty5x.pubsub.mapping.TopicImpl;
import cn.van.kuang.netty5x.pubsub.pub.Notification;
import cn.van.kuang.netty5x.server.util.ChannelHolder;
import cn.van.kuang.netty5x.server.util.TopicChannel;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Notifier {

    private final static Logger logger = LoggerFactory.getLogger(Notifier.class);

    public void start() {
        final Notification<String> notificationA = Notification.Builder.<String>get()
                .withName("TopicANotification")
                .withTopic(new TopicImpl(Topic.Type.A))
                .withContent("Message~~~")
                .build();

        final Notification<String> notificationB = Notification.Builder.<String>get()
                .withName("TopicBNotification")
                .withTopic(new TopicImpl(Topic.Type.B))
                .withContent("Message~~~")
                .build();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                (Runnable) () -> {
                    doNotify(notificationA);
                    doNotify(notificationB);
                }, 60, 60, TimeUnit.SECONDS
        );
    }

    public void doNotify(Notification message) {

        Collection<TopicChannel> channels = ChannelHolder.get();

        if (channels.isEmpty()) {
            return;
        }

        int count = 0;
        for (TopicChannel topicChannel : channels) {
            if (topicChannel.isMatched(message)) {

                Channel channel = topicChannel.getChannel();
                channel.writeAndFlush(message);
                count++;

                logger.info("Notified [{}] to {} client [{}]", message, channel.remoteAddress());
            }
        }

        logger.info("Notified to {} client(s)", count);

    }
}
