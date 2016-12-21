package cn.van.kuang.java.core.design.pattern.command;

import java.util.concurrent.atomic.AtomicInteger;

public class PlusCommand implements Command<AtomicInteger> {

    @Override
    public void execute(AtomicInteger integer) {
        System.out.println(
                this.getClass().getSimpleName() + ": OldValue=["
                        + integer.get()
                        + "], NewValue=["
                        + integer.incrementAndGet()
                        + "]");
    }

}
