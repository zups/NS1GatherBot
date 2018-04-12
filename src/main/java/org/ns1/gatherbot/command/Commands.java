package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;

public class Commands {
    private final List<Command> commands;

    public Commands(List<Command> commands) {
        this.commands = commands;
    }

    public Optional<Command> findCommand(String name) {
        return commands.stream().filter(c -> c.isItMe(name)).findFirst();
    }
}
