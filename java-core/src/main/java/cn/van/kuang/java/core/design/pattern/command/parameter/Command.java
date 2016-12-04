package cn.van.kuang.java.core.design.pattern.command.parameter;

import java.util.ArrayList;
import java.util.List;

public interface Command<T> {

    void execute(T t);

    class Builder<T> {

        private List<Command<T>> commands = new ArrayList<>();

        public Builder<T> add(Command<T> command) {
            this.commands.add(command);
            return this;
        }

        public List<Command<T>> build() {
            return commands;
        }
    }

    static void main(String[] args) {
        List<Command<Product>> commands = new Builder<Product>()
                .add(new PriceEnricher())
                .add(new WeightEnricher())
                .add(new FromEnricher())
                .build();

        Product water = new Product("Water");
        System.out.println("Before: " + water);

        for (Command<Product> command : commands) {
            command.execute(water);
        }

        System.out.println("After: " + water);
    }

}
