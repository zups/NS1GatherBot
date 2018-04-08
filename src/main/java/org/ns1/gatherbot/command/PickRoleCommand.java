package org.ns1.gatherbot.command;

import java.util.Optional;
import java.util.function.IntConsumer;
import net.dv8tion.jda.core.entities.impl.EmoteImpl;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Players;

public class PickRoleCommand extends AbstractCommand {
    private Players players;
    private Lifeforms lifeforms;

    public PickRoleCommand(Players players, Lifeforms lifeforms) {
        super("roles");
        this.players = players;
        this.lifeforms = lifeforms;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        parameters.getEmote().ifPresent(emote -> {
            lifeforms.getEmote(parameters.getEmote().get().getName())
                    .ifPresent(emo -> players.updateRoles(parameters.getUser(), parameters.getEmote().get(), parameters.getMessageId().getMessageId()));
        });

        return Optional.empty();
    }
}
