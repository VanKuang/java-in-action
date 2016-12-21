package cn.van.kuang.java.core.design.pattern.command;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiplyCommand implements Command<AtomicInteger> {

    private static final int DEFAULT_FACTOR = 10;

    @Override
    public void execute(AtomicInteger integer) {
        System.out.println(
                this.getClass().getSimpleName() + ": OldValue=["
                        + integer.getAndSet(integer.get() * DEFAULT_FACTOR)
                        + "], NewValue=["
                        + integer.get()
                        + "]");
    }
}
