package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;

public class JoinCommandImpl implements ICommand {

    private String name = "join";

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(IUser user) {
        return null;
    }

    @Override
    public Optional<String> run(IUser user, Players players) {
        return players.addPlayer(new Player(user));
    }
}
