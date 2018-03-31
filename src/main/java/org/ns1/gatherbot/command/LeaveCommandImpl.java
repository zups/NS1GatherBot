package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;

public class LeaveCommandImpl implements ICommand {
    private String name = "leave";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(IUser user) {
        return "mie lähen";
    }

    @Override
    public Optional<String> run(IMessage message, Players players) {
        IUser user = message.getAuthor();
        IChannel channel = message.getChannel();
        return players.removePlayer(new Player(user));
    }
}
