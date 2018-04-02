package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public interface Command {

    boolean isItMe(String name);

    String run(User user);

    Optional<String> run(Message message);

    Optional<String> run(User user, Emote emote);

}
