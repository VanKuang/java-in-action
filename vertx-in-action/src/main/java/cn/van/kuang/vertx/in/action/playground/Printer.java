package cn.van.kuang.vertx.in.action.playground;

public final class Printer {

    public static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]--" + message);
    }

    private Printer() {
    }
}
