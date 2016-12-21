package cn.van.kuang.java.core.design.pattern.state;

public class Context {

    private State state;

    public State getState() {
        return state;
    }

    public void changeState(State state) {
        this.state = state;
    }
}
