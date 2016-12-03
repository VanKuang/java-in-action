package cn.van.kuang.java.core.design.pattern.command;

import java.util.ArrayList;
import java.util.List;

public final class CommandAssembler {

    private List<Command> commands = new ArrayList<>();

    public CommandAssembler add(Command command) {
        commands.add(command);
        return this;
    }

    public List<Command> build() {
        return commands;
    }

}
