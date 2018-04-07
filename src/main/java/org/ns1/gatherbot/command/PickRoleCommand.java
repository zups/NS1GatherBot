package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Players;
import java.util.Optional;

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
    public Optional<String> run() {
        lifeforms.getEmote(super.getEmote().getName())
                .ifPresent(emo -> players.updateRoles(super.getUser(), super.getEmote(), super.getMessageId().getMessageId()));

        return Optional.empty();
    }
}
