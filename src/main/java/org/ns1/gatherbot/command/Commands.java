package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Players;
import java.util.List;
import java.util.Optional;

public class Commands {
    private List<ICommand> commands;

    public Commands(List<ICommand> commands) {
        this.commands = commands;
    }

    public Optional<String> execute(String name, Message message, Players players) {

        StringBuilder returnMessage = new StringBuilder();
        commands.stream()
                .filter(c -> c.isItMe(name))
                .map(c -> c.run(message, players))
                .findFirst()
                .ifPresent(optMessage -> optMessage
                        .ifPresent(mes -> returnMessage.append(mes)));

        if (!returnMessage.toString().isEmpty()) {
            return Optional.of(returnMessage.toString());
        }

        return Optional.empty();
    }

    public Optional<String> execute(String name, User user) {

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
