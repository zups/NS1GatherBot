package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Players;
import java.util.Optional;

public class ListCommand implements Command {
    private String name = "list";
    private Players players;

    public ListCommand(Players players) {
        this.players = players;
    }

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
    public Optional<String> run(User user, Emote emote) {
        return null;
    }

    @Override
    public Optional<String> run(Message message) {
        return Optional.of(players.printPlayers());
    }
}

