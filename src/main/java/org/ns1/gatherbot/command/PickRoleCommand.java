package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
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
    public Optional<String> run(User user, Emote emote, String messageId) {
        lifeforms.getEmote(emote.getName())
                .ifPresent(emo -> players.updateRoles(user, emote, messageId));

        return Optional.empty();
    }
}
