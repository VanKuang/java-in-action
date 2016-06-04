package cn.van.kuang.netty5x.pubsub.mapping;

import java.io.Serializable;

public interface Topic extends Serializable {

    Type getType();

    enum Type {
        A,
        B,
        ALL
    }

}
