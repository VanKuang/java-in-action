package cn.van.kuang.java.core.design.pattern.visitor;

public class Developer implements Staff {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
