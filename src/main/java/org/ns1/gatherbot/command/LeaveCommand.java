package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

public class LeaveCommand extends AbstractCommand {
    private Players players;

    public LeaveCommand(Players players) {
        super("leave");
        this.players = players;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        return players.removePlayer(new Player(parameters.getUser()));
    }
}
