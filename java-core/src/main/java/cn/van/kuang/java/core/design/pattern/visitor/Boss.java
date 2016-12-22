package cn.van.kuang.java.core.design.pattern.visitor;

public class Boss implements Visitor {
    @Override
    public void visit(Staff staff) {
        if (staff instanceof Developer) {
            System.out.println("Boss: Show me your code");
        } else if (staff instanceof Tester) {
            System.out.println("Boss: Show me the test result");
        } else {
            throw new IllegalArgumentException("No such staff");
        }
    }
}
