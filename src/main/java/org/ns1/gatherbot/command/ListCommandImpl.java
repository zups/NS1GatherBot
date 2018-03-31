package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public class ListCommandImpl implements ICommand {
    private String name = "list";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(User user) {
        return "apua ketä täällä pelaa!!";
    }

    @Override
    public Optional<String> run(Message message, Players players) {
        return Optional.of(players.printPlayers());
    }
}

