package cn.van.kuang.java.core.design.pattern.visitor;

import java.math.BigDecimal;

public class HR implements Visitor {

    @Override
    public void visit(Staff staff) {
        System.out.println("HR: It's time to increase your salary, before: "
                + staff.salary()
                + ", after: "
                + staff.salary().multiply(new BigDecimal("1.1")));
    }

}
