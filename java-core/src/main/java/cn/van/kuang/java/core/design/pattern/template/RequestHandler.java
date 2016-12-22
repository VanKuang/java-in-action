package cn.van.kuang.java.core.design.pattern.template;

public interface RequestHandler {

    void onRequest(Request request);

    static void main(String[] args) {
        RequestHandler requestHandler = new DefaultRequestHandler();
        requestHandler.onRequest(new Request(1, "DATA"));

        try {
            requestHandler.onRequest(new Request(0, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            requestHandler.onRequest(new Request(2, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
