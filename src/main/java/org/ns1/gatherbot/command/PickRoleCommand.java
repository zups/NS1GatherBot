package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public class PickRoleCommand implements Command {
    private Players players;
    private Lifeforms lifeforms;

    public PickRoleCommand(Players players, Lifeforms lifeforms) {
        this.players = players;
        this.lifeforms = lifeforms;
    }

    @Override
    public boolean isItMe(String name) {
        if (name.equalsIgnoreCase("roles")) {
            return true;
        }
        return false;
    }

    @Override
    public String run(User user) {
        return null;
    }

    @Override
    public Optional<String> run(Message message) {
        return null;
    }

    @Override
    public Optional<String> run(User user, Emote emote) {
        lifeforms.getEmote(emote.getName())
                .ifPresent(emo -> players.updateRoles(user, emote));

        return Optional.empty();
    }
}
