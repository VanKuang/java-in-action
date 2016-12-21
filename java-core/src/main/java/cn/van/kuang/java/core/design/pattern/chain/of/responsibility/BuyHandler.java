package cn.van.kuang.java.core.design.pattern.chain.of.responsibility;

public class BuyHandler implements Handler {

    @Override
    public boolean isMatched(Request request) {
        return Request.Type.BUY == request.getType();
    }

    @Override
    public void doHandle(Request request) {
        System.out.println("Handle [" + request + "] by " + this.getClass().getSimpleName());
    }
}
