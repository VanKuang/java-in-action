package cn.van.kuang.hello.world.java.others;

import java.util.concurrent.atomic.AtomicInteger;

public enum EnumStyleFactory implements Factory<Integer> {

    INSTANCE;

    private AtomicInteger integer = new AtomicInteger(0);

    public Integer create() {
        return integer.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(EnumStyleFactory.INSTANCE.create());
        System.out.println(EnumStyleFactory.INSTANCE.create());
    }
}
