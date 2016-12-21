package cn.van.kuang.java.core.design.pattern.mediator.user;

public class Tester implements Role {

    private final String name;

    public Tester(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Type type() {
        return Type.TESTER;
    }

    @Override
    public void onMessage(String msg) {
        System.out.println(this.name + " received [" + msg + "]");
    }
}
