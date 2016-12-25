package cn.van.kuang.java.core.design.pattern.prototype.domain;

public class Manager implements Job {

    private final String title;

    public Manager(String title) {
        this.title = title;
    }

    @Override
    public Job clone() {
        return new Manager(title);
    }

    @Override
    public String title() {
        return this.title;
    }
}
