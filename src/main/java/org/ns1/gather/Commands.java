package org.ns1.gather;

import org.ns1.gather.command.*;
import sx.blah.discord.handle.obj.IUser;
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

    public Optional<String> execute(String name, IUser user) {

        StringBuilder message = new StringBuilder();
        commands.stream()
                .filter(c -> c.isItMe(name))
                .map(c -> c.run(user))
                .findFirst().ifPresent(mes -> message.append(mes));

        if (!message.toString().isEmpty()) {
            return Optional.of(message.toString());
        }


        return Optional.empty();
    }
}
