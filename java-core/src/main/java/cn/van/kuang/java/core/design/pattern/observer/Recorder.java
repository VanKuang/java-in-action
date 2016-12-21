package cn.van.kuang.java.core.design.pattern.observer;

public class Recorder implements Observer {
    @Override
    public void onAction(Action action) {
        System.out.println("Marked down " + action);
    }
}
