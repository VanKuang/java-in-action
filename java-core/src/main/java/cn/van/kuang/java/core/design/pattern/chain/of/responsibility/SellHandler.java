package cn.van.kuang.java.core.design.pattern.chain.of.responsibility;

public class SellHandler implements Handler {

    @Override
    public boolean isMatched(Request request) {
        return Request.Type.SELL == request.getType();
    }

    @Override
    public void doHandle(Request request) {
        System.out.println("Handle [" + request + "] by " + this.getClass().getSimpleName());
    }
}
