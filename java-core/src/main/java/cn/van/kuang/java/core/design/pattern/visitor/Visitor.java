package cn.van.kuang.java.core.design.pattern.visitor;

public interface Visitor {

    void visit(Staff staff);

    static void main(String[] args) {
        Developer developer = new Developer();
        Tester tester = new Tester();

        Boss boss = new Boss();

        developer.accept(boss);
        tester.accept(boss);
    }

}
