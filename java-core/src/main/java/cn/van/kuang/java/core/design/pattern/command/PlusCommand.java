package cn.van.kuang.java.core.design.pattern.command;

import java.util.concurrent.atomic.AtomicInteger;

public class PlusCommand implements Command {

    private final AtomicInteger originalValue;

    public PlusCommand(AtomicInteger originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public void execute() {
        System.out.println("OldValue=[" + originalValue.get() + "], NewValue=[" + originalValue.incrementAndGet() + "]");
    }

}
