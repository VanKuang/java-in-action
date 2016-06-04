package cn.van.kuang.netty5x.pubsub.sub;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;

import java.util.ArrayList;
import java.util.List;

class SubscriptionImpl implements Subscription {

    private String name;
    private List<Topic> topics = new ArrayList<Topic>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void addTopic(Topic topic) {
        if (topic == null) {
            throw new IllegalArgumentException("topic can't be null");
        }
        this.topics.add(topic);
    }

    public void addTopics(List<Topic> topics) {
        if (topics == null) {
            throw new IllegalArgumentException("topics can't be null");
        }
        this.topics.addAll(topics);
    }

    @Override
    public String toString() {
        return "SubscriptionImpl{" +
                "name='" + name + '\'' +
                ", topics=" + topics +
                '}';
    }
}
