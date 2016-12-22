package cn.van.kuang.java.core.design.pattern.strategy;

public interface Strategy<INPUT, RESULT> {

    RESULT execute(INPUT[] t);

}