package cn.van.kuang.netty5x.pubsub.mapping;

public class TopicImpl implements Topic {

    private final Type type;

    public TopicImpl(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TopicImpl{" +
                "type=" + type.name() +
                '}';
    }
}
