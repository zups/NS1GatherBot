package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.datastructure.Players;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class ListCommand extends AbstractCommand {
    private Players players;

    public ListCommand(Players players) {
        super("list");
        this.players = players;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        return Optional.of(PrettyPrints.printStatusAndPlayers(players.getPlayers(), players.getMaxPlayers()));
    }
}