package cn.van.kuang.java.core.design.pattern.visitor;

public interface Visitor {

    void visit(Staff staff);

    static void main(String[] args) {
        Staff developer = new Developer();
        Staff tester = new Tester();

        Visitor boss = new Boss();
        Visitor hr = new HR();

        developer.accept(boss);
        tester.accept(boss);

        developer.accept(hr);
        tester.accept(hr);
    }

}
