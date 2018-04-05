package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Message;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public class ListCommand extends AbstractCommand {
    private Players players;

    public ListCommand(Players players) {
        super("list");
        this.players = players;
    }

    @Override
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run(Message message) {
        return Optional.of(players.printPlayers());
    }
}

