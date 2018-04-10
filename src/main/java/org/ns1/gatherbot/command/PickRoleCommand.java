package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.datastructure.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Players;

public class PickRoleCommand extends AbstractCommand {
    private Players players;
    private LifeformEmojis lifeformEmojis;

    public PickRoleCommand(Players players, LifeformEmojis lifeformEmojis) {
        super("roles");
        this.players = players;
        this.lifeformEmojis = lifeformEmojis;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        parameters.getEmote().ifPresent(emote -> {
            lifeformEmojis.getEmote(parameters.getEmote().get().getName())
                    .ifPresent(emo -> players.updateRoles(parameters.getUser(), parameters.getEmote().get(), parameters.getMessageId().getMessageId()));
        });

        return Optional.empty();
    }
}
