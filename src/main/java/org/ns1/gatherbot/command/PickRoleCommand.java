package org.ns1.gatherbot.command;

import java.util.Optional;
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
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        lifeforms.getEmote(parameters.getEmote().getName())
                .ifPresent(emo -> players.updateRoles(parameters.getUser(), parameters.getEmote(), parameters.getMessageId().getMessageId()));

        return Optional.empty();
    }
}
