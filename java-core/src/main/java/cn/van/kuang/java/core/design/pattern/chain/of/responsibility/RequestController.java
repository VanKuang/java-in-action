package cn.van.kuang.java.core.design.pattern.chain.of.responsibility;

import java.util.ArrayList;
import java.util.List;

public class RequestController {

    private final List<Handler> handlers;

    public RequestController() {
        this.handlers = new ArrayList<>();
        this.handlers.add(new BuyHandler());
        this.handlers.add(new SellHandler());
    }

    public void onRequest(Request request) {
        if (request == null) {
            System.out.println("[WARN] Request is null, won't handle it");
            return;
        }

        for (Handler handler : handlers) {
            if (handler.isMatched(request)) {
                handler.doHandle(request);
                return;
            }
        }

        System.out.println("[WARN] No matched handler for request type=[" + request.getType() + "]");
    }

    public static void main(String[] args) {
        RequestController controller = new RequestController();

        controller.onRequest(new Request(1, Request.Type.BUY));
        controller.onRequest(new Request(2, Request.Type.SELL));
        controller.onRequest(new Request(2, Request.Type.UNKNOWN));
        controller.onRequest(null);
    }
}
