package cn.van.kuang.java.core.design.pattern.command;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommandExecutor {

    public void execute(List<Command> commands) {
        for (Command command : commands) {
            command.execute();
        }
    }

    public static void main(String[] args) {
        AtomicInteger number = new AtomicInteger(1);

        System.out.println("Before:" + number.get());

        List<Command> commands = new CommandAssembler()
                .add(new PlusCommand(number))
                .add(new MultiplyCommand(number))
                .add(new PlusCommand(number))
                .add(new MultiplyCommand(number))
                .build();

        new CommandExecutor().execute(commands);

        System.out.println("After:" + number.get());
    }

}
