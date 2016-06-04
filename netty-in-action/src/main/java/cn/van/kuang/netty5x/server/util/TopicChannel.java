package cn.van.kuang.netty5x.server.util;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;
import cn.van.kuang.netty5x.pubsub.pub.Notification;
import io.netty.channel.Channel;

import java.util.List;

public class TopicChannel {

    private final List<Topic> topics;
    private final Channel channel;

    public TopicChannel(List<Topic> topics, Channel channel) {
        this.topics = topics;
        this.channel = channel;
    }

    public boolean isMatched(Notification notification) {
        for (Topic topic : topics) {
            if (Topic.Type.ALL == topic.getType()
                    || topic.getType() == notification.getTopic().getType()) {
                return true;
            }
        }
        return false;
    }

    public Channel getChannel() {
        return channel;
    }
}
