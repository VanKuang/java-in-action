package cn.van.kuang.java.core.design.pattern.visitor;

public class Tester implements Staff {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
