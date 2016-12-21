package cn.van.kuang.java.core.design.pattern.state;

public class OpenState implements State {

    @Override
    public void onStateChange(Context context) {
        context.changeState(this);
    }

    @Override
    public String name() {
        return "Open";
    }

    @Override
    public void handle() {
        System.out.println("Open for business");
    }

}
