package cn.van.kuang.java.core.design.pattern.visitor;

import java.math.BigDecimal;

public class Tester implements Staff {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String showWorkingResult() {
        return "Here is the acceptance result";
    }

    @Override
    public BigDecimal salary() {
        return new BigDecimal(9000);
    }
}
