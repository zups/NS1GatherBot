package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.util.GatherRules;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class ListCommand extends AbstractCommand {
    private final PlayerController playerController;

    public ListCommand(PlayerController playerController) {
        super("list");
        this.playerController = playerController;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        return Optional.of(new CommandResult(
                Optional.of(PrettyPrints.printStatusAndPlayers(playerController.getPlayers(), GatherRules.getRules().getMaxPlayers()))
                ,true));
    }
}