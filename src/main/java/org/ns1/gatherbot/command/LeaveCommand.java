package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.util.ParameterWrapper;

public class LeaveCommand extends AbstractCommand {
    private final Players players;

    public LeaveCommand(Players players) {
        super("leave");
        this.players = players;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        players.removePlayer(parameters.getPlayer())
                .ifPresent(player -> {
                    result.setMessage(Optional.of(player.getName() + " left."));
                    result.setRunSuccessful(true);
                });

        return Optional.of(result);
    }
}
