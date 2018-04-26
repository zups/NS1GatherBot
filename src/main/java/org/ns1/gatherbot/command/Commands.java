package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Commands {
    private final List<Command> commands;

    public Optional<Command> findCommand(String name) {
        return commands.stream().filter(c -> c.isItMe(name)).findFirst();
    }
}
