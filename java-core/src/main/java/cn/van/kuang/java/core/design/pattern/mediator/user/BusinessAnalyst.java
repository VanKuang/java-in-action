package cn.van.kuang.java.core.design.pattern.mediator.user;

public class BusinessAnalyst implements Role {

    private final String name;

    public BusinessAnalyst(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Type type() {
        return Type.BA;
    }

    @Override
    public void onMessage(String msg) {
        System.out.println(this.name + " received [" + msg + "]");
    }
}
