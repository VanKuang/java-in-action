package cn.van.kuang.java.core.design.pattern.chain.of.responsibility;

public interface Handler {

    boolean isMatched(Request request);

    void doHandle(Request request);

}
