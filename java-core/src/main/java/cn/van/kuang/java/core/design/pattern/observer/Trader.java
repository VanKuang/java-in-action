package cn.van.kuang.java.core.design.pattern.observer;

public class Trader implements Observer {

    private final String name;

    public Trader(String name) {
        this.name = name;
    }

    @Override
    public void onAction(Action action) {
        System.out.println(this.name + " received " + action);
    }

}
