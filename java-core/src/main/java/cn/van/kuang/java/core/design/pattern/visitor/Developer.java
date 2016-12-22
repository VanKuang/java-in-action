package cn.van.kuang.java.core.design.pattern.visitor;

import java.math.BigDecimal;

public class Developer implements Staff {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String showWorkingResult() {
        return "Here is my piece of code";
    }

    @Override
    public BigDecimal salary() {
        return new BigDecimal(10000);
    }
}
