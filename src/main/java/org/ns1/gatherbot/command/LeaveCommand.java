package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;

public class LeaveCommand extends AbstractCommand {
    private final PlayerController playerController;

    public LeaveCommand(PlayerController playerController) {
        super("leave");
        this.playerController = playerController;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        StringBuilder message = new StringBuilder();

        playerController.removePlayer(new Player(parameters.getUser()))
                .ifPresent(player -> message.append(player.getName() + " left."));

        return message.length() > 0 ? Optional.of(new CommandResult(message.toString(), true)) : Optional.empty();
    }
}
