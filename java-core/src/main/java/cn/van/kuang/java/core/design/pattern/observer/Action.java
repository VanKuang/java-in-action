package cn.van.kuang.java.core.design.pattern.observer;

public interface Action {

    Type type();

    enum Type {
        BID,
        ASK
    }
}
