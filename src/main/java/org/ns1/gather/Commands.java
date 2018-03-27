package org.ns1.gather;

import org.ns1.gather.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Commands {
    private List<ICommand> commands = Arrays.asList(
            new JoinCommandImpl(),
            new LeaveCommandImpl(),
            new PickCommandImpl(),
            new ListCommandImpl()
    );

    //private Stream<ICommand> commands = Stream.of(new JoinCommandImpl());

    public Optional<String> execute(String name) {

        StringBuilder message = new StringBuilder();
        commands.stream()
                .filter(c -> c.isItMe(name))
                .map(ICommand::run).findFirst().ifPresent(mes -> message.append(mes));

        if (!message.toString().isEmpty()) {
            return Optional.of(message.toString());
        }


        return Optional.empty();
    }
}
