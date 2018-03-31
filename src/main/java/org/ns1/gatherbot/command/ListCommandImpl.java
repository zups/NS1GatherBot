package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;

public class ListCommandImpl implements ICommand {
    private String name = "list";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(IUser user) {
        return "apua ketä täällä pelaa!!";
    }

    @Override
    public Optional<String> run(IMessage message, Players players) {
        return Optional.of(players.printPlayers());
    }
}

