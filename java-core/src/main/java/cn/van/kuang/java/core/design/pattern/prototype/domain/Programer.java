package cn.van.kuang.java.core.design.pattern.prototype.domain;

public class Programer implements Job {

    private final String title;

    public Programer(String title) {
        this.title = title;
    }

    @Override
    public Job clone() {
        return new Programer(title);
    }

    @Override
    public String title() {
        return this.title;
    }
}
