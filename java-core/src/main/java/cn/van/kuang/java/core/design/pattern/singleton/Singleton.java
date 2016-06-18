package cn.van.kuang.java.core.design.pattern.singleton;

public class Singleton {

    public static Singleton instance() {
        return Holder.singleton;
    }

    private static class Holder {
        private final static Singleton singleton = new Singleton();
    }

    private Singleton() {
    }

    public static void main(String[] args) {
        System.out.println(Singleton.instance() == Singleton.instance());
    }
}
