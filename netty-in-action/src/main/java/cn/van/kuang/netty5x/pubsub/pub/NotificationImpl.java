package cn.van.kuang.netty5x.pubsub.pub;

import cn.van.kuang.netty5x.pubsub.mapping.Topic;

class NotificationImpl<T> implements Notification<T> {

    private String name;
    private Topic topic;
    private T content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NotificationImpl{" +
                "name='" + name + '\'' +
                ", topic=" + topic +
                ", content=" + content +
                '}';
    }
}
