package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;

public interface ICommand {

    boolean isItMe(String name);

    String run(IUser user);

    Optional<String> run(IUser user, Players players);

}
