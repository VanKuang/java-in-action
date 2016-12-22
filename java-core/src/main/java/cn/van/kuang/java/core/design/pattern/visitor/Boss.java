package cn.van.kuang.java.core.design.pattern.visitor;

public class Boss implements Visitor {
    @Override
    public void visit(Staff staff) {
        System.out.println("Boss: Show me your work");
        System.out.println(staff.getClass().getSimpleName() + ": " + staff.showWorkingResult());
    }
}
