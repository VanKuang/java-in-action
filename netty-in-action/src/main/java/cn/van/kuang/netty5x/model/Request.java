package cn.van.kuang.netty5x.model;

import java.io.Serializable;

public interface Request extends Serializable {

    enum Type {
        DATABASE,
        CACHE
    }

    Type getType();

}
