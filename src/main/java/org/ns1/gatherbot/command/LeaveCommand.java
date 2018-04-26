package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;

public class LeaveCommand extends AbstractCommand {
    private final Players players;

    public LeaveCommand(Players players) {
        super("leave");
        this.players = players;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        players.removePlayer(new Player(parameters.getUser()))
                .ifPresent(player -> {
                    result.setMessage(Optional.of(player.getName() + " left."));
                    result.setRunSuccessful(true);
                });

        return Optional.of(result);
    }
}
