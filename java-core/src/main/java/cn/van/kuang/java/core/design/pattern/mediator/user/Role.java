package cn.van.kuang.java.core.design.pattern.mediator.user;

public interface Role {

    String name();

    Type type();

    void onMessage(String msg);

    enum Type {
        DEV,
        TESTER,
        BA,
        MANAGER
    }
}
