package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.util.GatherRules;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class ListCommand extends AbstractCommand {
    private final Players players;

    public ListCommand(Players players) {
        super("list");
        this.players = players;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        return Optional.of(new CommandResult(
                Optional.of(PrettyPrints.printStatusAndPlayers(players.getPlayers(), GatherRules.getRules().getMaxPlayers()))
                ,true));
    }
}