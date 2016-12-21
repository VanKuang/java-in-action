package cn.van.kuang.java.core.design.pattern.state;

public class CloseState implements State {

    @Override
    public void onStateChange(Context context) {
        context.changeState(this);
    }

    @Override
    public String name() {
        return "Close";
    }

    @Override
    public void handle() {
        System.out.println("Do nothing");
    }

}
