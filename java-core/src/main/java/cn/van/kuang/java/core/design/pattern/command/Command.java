package cn.van.kuang.java.core.design.pattern.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Command<T> {

    void execute(T t);

    class Builder<T> {

        private List<Command<T>> commands = new ArrayList<>();

        public Builder<T> add(Command<T> command) {
            commands.add(command);
            return this;
        }

        public List<Command<T>> build() {
            return commands;
        }

    }

    class Executor<T> {

        private final Builder<T> builder;

        public Executor(Builder<T> builder) {
            this.builder = builder;
        }

        public void execute(T t) {
            List<Command<T>> commands = builder.build();
            for (Command<T> command : commands) {
                command.execute(t);
            }
        }
    }

    static void main(String[] args) {
        AtomicInteger number = new AtomicInteger(1);

        System.out.println("Before:" + number.get());

        Builder<AtomicInteger> builder = new Builder<AtomicInteger>()
                .add(new PlusCommand())
                .add(new MultiplyCommand());

        new Executor<>(builder).execute(number);

        System.out.println("After:" + number.get());
    }
}
