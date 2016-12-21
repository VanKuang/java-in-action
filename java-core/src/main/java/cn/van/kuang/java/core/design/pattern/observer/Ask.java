package cn.van.kuang.java.core.design.pattern.observer;

public class Ask implements Action {

    private final String ccyPair;

    public Ask(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public String ccyPair() {
        return ccyPair;
    }

    @Override
    public Type type() {
        return Type.ASK;
    }

    @Override
    public String toString() {
        return "Ask{" +
                "ccyPair=" + ccyPair() +
                ", type=" + type() +
                '}';
    }
}
