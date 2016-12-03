package cn.van.kuang.java.core.design.pattern.command;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiplyCommand implements Command {

    private static final int DEFAULT_FACTOR = 10;

    private final AtomicInteger originalValue;

    public MultiplyCommand(AtomicInteger originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public void execute() {
        System.out.println(
                "OldValue=[" + originalValue.getAndSet(originalValue.get() * DEFAULT_FACTOR)
                        + "], NewValue=[" + originalValue.get() + "]");
    }
}
