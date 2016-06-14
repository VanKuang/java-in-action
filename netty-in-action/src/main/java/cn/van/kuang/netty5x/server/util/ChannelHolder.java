package cn.van.kuang.netty5x.server.util;

import io.netty.channel.ChannelId;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChannelHolder {

    private static Map<ChannelId, TopicChannel> channels = new ConcurrentHashMap<>();

    public static void put(TopicChannel topicChannel) {
        channels.put(topicChannel.getChannel().id(), topicChannel);
    }

    public static void remove(ChannelId id) {
        channels.remove(id);
    }

    public static Collection<TopicChannel> get() {
        return channels.values();
    }

    public static TopicChannel get(ChannelId id) {
        for (Map.Entry<ChannelId, TopicChannel> entry : channels.entrySet()) {
            if (id.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private ChannelHolder() {
    }
}
