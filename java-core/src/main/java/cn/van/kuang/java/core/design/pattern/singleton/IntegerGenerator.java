package cn.van.kuang.java.core.design.pattern.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public enum IntegerGenerator implements Generator<Integer> {

    INSTANCE;

    private AtomicInteger integer = new AtomicInteger(0);

    public Integer generate() {
        return integer.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(IntegerGenerator.INSTANCE.generate());
        System.out.println(IntegerGenerator.INSTANCE.generate());
    }
}
