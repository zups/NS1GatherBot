package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class SetRoleCommand extends AbstractCommand {
    private final Players players;
    private final LifeformEmojis lifeformEmojis = LifeformEmojis.getEmojis();

    public SetRoleCommand(Players players) {
        super("roles");
        this.players = players;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        parameters.getEmote().ifPresent(emote -> {
            lifeformEmojis.getEmote(parameters.getEmote().get().getName())
                    .ifPresent(emo -> players.updateRoles(parameters.getUser(), parameters.getEmote().get(), parameters.getMessageId()));
        });

        result.setRunSuccessful(true);
        return Optional.of(result);
    }
}
