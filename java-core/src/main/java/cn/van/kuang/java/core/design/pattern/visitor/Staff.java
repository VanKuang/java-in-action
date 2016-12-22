package cn.van.kuang.java.core.design.pattern.visitor;

import java.math.BigDecimal;

public interface Staff {

    void accept(Visitor visitor);

    String showWorkingResult();

    BigDecimal salary();

}
