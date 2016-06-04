package cn.van.kuang.netty5x.pubsub.sub;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;

import java.io.Serializable;
import java.util.List;

public interface Subscription extends Serializable {

    String getName();

    List<Topic> getTopics();

    class Builder {

        public static Builder get() {
            return new Builder();
        }

        private SubscriptionImpl subscription;

        public Builder() {
            this.subscription = new SubscriptionImpl();
        }

        public Builder withName(String name) {
            subscription.setName(name);
            return this;
        }

        public Builder withTopic(Topic topic) {
            subscription.addTopic(topic);
            return this;
        }

        public Builder withTopics(List<Topic> topics) {
            subscription.addTopics(topics);
            return this;
        }

        public Subscription build() {
            return subscription;
        }
    }

}
