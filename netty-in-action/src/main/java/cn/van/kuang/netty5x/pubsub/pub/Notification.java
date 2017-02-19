package cn.van.kuang.netty5x.pubsub.pub;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;

import java.io.Serializable;

public interface Notification<T> extends Serializable {

    String getName();

    Topic getTopic();

    T getContent();

    class Builder<T> {

        public static <T> Builder<T> get() {
            return new Builder<>();
        }

        private NotificationImpl<T> notification;

        public Builder() {
            this.notification = new NotificationImpl<>();
        }

        public Builder<T> withName(String name) {
            notification.setName(name);
            return this;
        }

        public Builder<T> withTopic(Topic topic) {
            notification.setTopic(topic);
            return this;
        }

        public Builder<T> withContent(T content) {
            notification.setContent(content);
            return this;
        }

        public Notification<T> build() {
            return notification;
        }
    }

}
