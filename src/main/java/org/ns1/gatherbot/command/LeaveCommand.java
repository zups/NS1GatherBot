package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import java.util.Optional;

public class LeaveCommand extends AbstractCommand {
    private Players players;

    public LeaveCommand(Players players) {
        super("leave");
        this.players = players;
    }

    @Override
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        return players.removePlayer(new Player(parameters.getUser()));
    }
}
