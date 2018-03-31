package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

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
    public String run(User user) {
        return "mie lähen";
    }

    @Override
    public Optional<String> run(Message message, Players players) {
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();
        return players.removePlayer(new Player(user));
    }
}
