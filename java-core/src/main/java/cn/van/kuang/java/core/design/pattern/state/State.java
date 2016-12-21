package cn.van.kuang.java.core.design.pattern.state;

public interface State {

    void onStateChange(Context context);

    String name();

    void handle();

    static void main(String[] args) {
        Context context = new Context();
        State openState = new OpenState();
        State closeState = new CloseState();

        openState.onStateChange(context);
        System.out.println(context.getState().name());
        context.getState().handle();

        closeState.onStateChange(context);
        System.out.println(context.getState().name());
        context.getState().handle();

        closeState.onStateChange(context);
        System.out.println(context.getState().name());
        context.getState().handle();
    }

}
